package test.SpringMVC;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;

import net.sf.json.JSONArray;
import test.SpringMVC.model.FileInfo;
import test.SpringMVC.model.Measure;
import test.SpringMVC.model.User;

@Controller
@RequestMapping("/mvc")
public class mvcController {

    /**
     * 跳转到标记页面
     * @return
     */
    @RequestMapping("/getMark")
    public String getMark(){
    	return "mark";
    }

    
    /**
     * 跳转到项目管理模块
     * @return
     */
    @RequestMapping("/getManageMark")
    public String getManageMark(){
    	return "manageMark";
    }
    
    /**
     * 跳转到识别模块
     * @return
     */
    @RequestMapping("/getIdentification")
    public String getIdentification(){
		return "identification";
    }
    /**
     * 跳转到应用模块
     * @return
     */
    @RequestMapping("/getApplication")
    public String getApplication(){
    	return "application";
    }

	/**
	 * 跳转到文件识别应用
	 * @return
	 */
    @RequestMapping("getIdentyFile")
	public  String getIdentyFile(){
    	return "identyfile";
	}

	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping("getRegister")
	public String getRegister(){
		return "register";
	}

	/**
	 * 登录
	 * @return
	 */
	@RequestMapping("getLogin")
	public String getLogin(){
		return "login";
	}
    /**
     * 判断是否是Linux操作系统
     * @return
     */
    private boolean isOSLinux() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 删除选中的文件
     * @param request
     * @param response
     */
    @RequestMapping("/deleteFile")
    public void deleteFile(HttpServletRequest request,HttpServletResponse response){
    	String userId= request.getParameter("userId");
    	String fileName= request.getParameter("fileName");
    	PreparedStatement deleteStatment = null;
    	PreparedStatement queryStatment = null;
    	Connection conn = connectMysql();
    	ResultSet rs = null;
    	String url = null;
    	try {
    		queryStatment = conn.prepareStatement("select url from file where user_id = ? and file_name= ? ");
			queryStatment.setString(1,userId);
			queryStatment.setString(2, fileName);
			rs = queryStatment.executeQuery();
			while(rs.next()){
	    		url = rs.getString("url");
	    	}
			String filePath = url.substring(0, url.lastIndexOf('/'));
			System.out.println(filePath);
			//linux系统使用如下命令,成功运行
			String cmdLinux[]= {"rm "+filePath+"/"+fileName };
			//windows系统使用如下命令,成功运行
			String cmdWin[] ={ "cmd","/C","del "+filePath+"\\"+ fileName};
			String cmd[] = isOSLinux() ? cmdLinux : cmdWin;
			if(isOSLinux()){
				Runtime.getRuntime().exec(cmdLinux);
			}else{
				Runtime.getRuntime().exec(cmdWin);
			}
    		deleteStatment = conn.prepareStatement("delete from file where user_id = ? and file_name = ?");
    		deleteStatment.setString(1,userId);
    		deleteStatment.setString(2, fileName);
	    	int result = deleteStatment.executeUpdate();
	    	conn.commit();
	    	if(result > 0){
	    		System.out.println("操作成功"+result);
	    	}else {
	    		System.out.println("操作失败");
	    	}
	    	deleteStatment.close();
	    	conn.close();
		} catch(Exception e){
			e.printStackTrace();
		}
    }

    @RequestMapping("/identityFile")
	public void identityFile(HttpServletRequest request,HttpServletResponse response){
		String modelPath = request.getParameter("modelUrl");
		String sourcePath = request.getServletContext().getRealPath("/resources/trainAndTest/") + "/"+"temp.data";
		String testPath = request.getServletContext().getRealPath("/resources/trainAndTest/");
		String filePath = request.getParameter("fileUrl");
		response.setContentType("text/html;charset=utf-8");
		try {
			FileOutputStream fos = new FileOutputStream(sourcePath);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			File file = new File(filePath);
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while((lineTxt = bufferedReader.readLine()) != null){
				if(lineTxt.length()<1){
					continue;
				}
				CoNLLSentence sentence = HanLP.parseDependency(lineTxt);
				for(CoNLLWord word : sentence){
					for(int p=0;p<word.LEMMA.length();p++)
						osw.write(word.LEMMA.charAt(p)+"\t"+word.POSTAG+"\t"+map.get(word.DEPREL)+"\t\n");
				}
				osw.write("\n");
				osw.flush();
			}
			osw.close();
			identityPrint(response,modelPath,testPath,sourcePath);
		}catch (Exception e){

		}
	}
    /**
     * 将用户输入的文本识别之后返回
     * @param request
     * @param response
     */
    @RequestMapping("/identity")
    public void identity(HttpServletRequest request,HttpServletResponse response){
    	String modelPath = request.getParameter("modelUrl");
    	String sourcePath = request.getServletContext().getRealPath("/resources/trainAndTest/") + "/"+"temp.data";
    	String testPath = request.getServletContext().getRealPath("/resources/trainAndTest/");
    	response.setContentType("text/html;charset=utf-8");
    	try {
			FileOutputStream fos = new FileOutputStream(sourcePath);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			String str = request.getParameter("text");
			String [] strData = str.split("\n");
			for (String strTemp : strData){
				CoNLLSentence sentence = HanLP.parseDependency(strTemp);
				for(CoNLLWord word : sentence){
					for(int p=0;p<word.LEMMA.length();p++)
					osw.write(word.LEMMA.charAt(p)+"\t"+word.POSTAG+"\t"+map.get(word.DEPREL)+"\t\n");
				}
				osw.write("\n");
				osw.flush();
			}
			osw.close();
			identityPrint(response,modelPath,testPath,sourcePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	/**
	 * 实体识别主要代码
	 * @param response
	 * @param modelPath
	 * @param testPath
	 * @param sourcePath
	 * @throws Exception
	 */
    public void identityPrint(HttpServletResponse response,String modelPath,String testPath,String sourcePath) throws Exception {
		String cmdLinux= "crf_test -m "+modelPath +" " + sourcePath;
		String cmdWin = testPath+"/crf_test -m "+ modelPath+" "+ sourcePath;
		String cmd = isOSLinux() ? cmdLinux : cmdWin;
		System.out.println(cmd);
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader br  = new BufferedReader(new InputStreamReader(p.getInputStream(),"UTF-8"));
		String line = null;
		PrintWriter pw = response.getWriter();
		while ((line = br.readLine()) != null) {
			String strLine[] = line.split("\t");
			if(line.length() < 1){
				pw.write("\n");
				continue ;
			}
			if(strLine[3].equals("B"))
				pw.write("#");
			pw.write(line.charAt(0));
			if(strLine[3].equals("E"))
				pw.write("#");
		}
	}
    /**
     * 测试生成模型的效果，计算三个参数的精度。
     * @param request
     * @param response
     */
    @RequestMapping("/testModel")
    public void testModel(HttpServletRequest request,HttpServletResponse response){
    	response.setContentType("text/html;charset=utf-8");
    	String modelPath = request.getParameter("modelUrl");
    	String testFilePath = request.getParameter("testFileUrl");
		String cmdLinux = "crf_test -m "  + modelPath + " " + testFilePath;
		String testPath = request.getServletContext().getRealPath("/resources/trainAndTest/");
		String cmdWin = testPath+"/crf_test -m "+ modelPath +" "+ testFilePath;
		String cmdStr = isOSLinux() ? cmdLinux : cmdWin;
		System.out.println(cmdStr);
    	PrintWriter out;
		Process p;
		try {
			p = Runtime.getRuntime().exec(cmdStr);
			out = response.getWriter();
			BufferedReader br  = new BufferedReader(new InputStreamReader(p.getInputStream(),"UTF-8"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			int correct = 0;
			int testData = 0;
			int masterData = 0;
			String [] strArray = null;
			while ((line = br.readLine()) != null) {
				sb.append(line+"\n");
				strArray = line.split("\t");
				if(strArray.length >= 5){
					if( strArray[4].equals("E") ){
						testData += 1;
						if(strArray[3].equals("E")){
							correct += 1;
						}
					}
					if(strArray[3].equals("E")){
						masterData += 1;
					}
				}
			}
			double accuracyRate = correct*1.0/testData;
			double recallRate = correct*1.0/masterData;
			double fValue = 2*accuracyRate*recallRate/(accuracyRate+recallRate);
			Measure measure = new Measure();
			measure.setTestData(sb.toString());
			measure.setAccuracyRate(Double.toString(accuracyRate));
			measure.setRecallRate(Double.toString(recallRate));
			measure.setfValue(Double.toString(fValue));
			ArrayList<Measure> list = new ArrayList<Measure>();
			list.add(measure);
			JSONArray json = JSONArray.fromObject(list);
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * 将选择的格式化文件训练生成crf模型
     * @param request
     * @param response
     */
    @RequestMapping("/train")
    public void train(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
    	String trainFilePath = request.getParameter("trainFileUrl");
    	String templateFilePath = request.getParameter("templateFileUrl");
    	String parameter = request.getParameter("parameter");
    	String userId = request.getParameter("user_id");

    	String fileName = trainFilePath.substring(trainFilePath.lastIndexOf('_') + 1,trainFilePath.indexOf('.'));
    	String modelPath = request.getServletContext().getRealPath("/resources/model/")+"/"+fileName+"_model";

		String cmdLinux = "crf_learn " + parameter + " " + templateFilePath + " "+trainFilePath+" " + modelPath;
		String trainPath = request.getServletContext().getRealPath("/resources/trainAndTest/");
		String cmdWin = trainPath+"/crf_learn "+ parameter +" "+ templateFilePath + " "+trainFilePath+" " + modelPath;
		String cmdStr = isOSLinux() ? cmdLinux : cmdWin;
		System.out.println(cmdStr);

    	PrintWriter out;
    	Process p;
		try {
			p = Runtime.getRuntime().exec(cmdStr);
			BufferedReader br  = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			out = response.getWriter();
			while ((line = br.readLine()) != null) {
				out.write(line+"\n");
			}
			File file = new File(modelPath);
			if(file.exists()){
				int userID = Integer.valueOf(userId).intValue();
				insertFileInfo(request.getServletContext().getRealPath("/resources/model/"),"D",fileName+"_model",userID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }


    /**
     * 将标记后的文本内容转为crf训练需要的格式
     * @param filePah
     * @param targetPath
     * @return
     */
    private String filter(String filePah, String targetPath){

    	String result = null;
    	try {
            String encoding="UTF-8";
            File file=new File(filePah);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                boolean startTag = true;
                boolean hasQueue = false;
                boolean hasMarked = false;
                Queue<String> queue = new LinkedList<String>();
                FileWriter fw  = new FileWriter(targetPath,false);
                while((lineTxt = bufferedReader.readLine()) != null){
                	startTag = true;
                	hasQueue = false;
                	int j = 0;
                	hasMarked  = false;
                	while(j<lineTxt.length()){
                		if(lineTxt.charAt(j++)=='#'){
                			hasMarked = true;
                			break;
                		}
                	}
                	if(!hasMarked){
                		continue;
                	}
                	CoNLLSentence sentence = HanLP.parseDependency(lineTxt);
                	for(CoNLLWord word : sentence){
                		if('#' == word.LEMMA.charAt(0)&&startTag){
                			startTag = false;
                		}else if('#'!= word.LEMMA.charAt(0)&&(!startTag)){
                			for(int p=0;p<word.LEMMA.length();p++){
                				queue.offer(word.LEMMA.charAt(p)+"\t"+word.POSTAG+"\t"+map.get(word.DEPREL));
                			}
                			hasQueue = true;
                		}else if('#'!= word.LEMMA.charAt(0)&& startTag ){
                			for(int p=0;p<word.LEMMA.length();p++){
                				fw.write(word.LEMMA.charAt(p)+"\t"+word.POSTAG+"\t"+map.get(word.DEPREL)+"\t"+"O\n");
                			}
                		}else if('#' == word.LEMMA.charAt(0)&& !startTag ){
                			startTag = true;
                			if(hasQueue){
                				fw.write(queue.poll()+"\t"+"B\n");
                				while(!queue.isEmpty()){
                					if(1 != queue.size()){
                						fw.write(queue.poll()+"\t"+"M\n");
                					}else{
                						fw.write(queue.poll()+"\t"+"E\n");
                					}
                				}
                			}
                			hasQueue = false;
                		}
                	}
                	fw.write("\n");
                }
                read.close();
                fw.close();
                result="格式化成功";
        }else{
            System.out.println("找不到指定的文件");
            result = "找不到指定文件";
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            result = "读取文件内容出错";
        }
    	return result;
    }
    
    /**
     * 过滤无标记的句子，将内容格式化为crf训练需要的格式
     * @param request
     * @param response
     */
    @RequestMapping("/formatText")
    public void formatText(HttpServletRequest request,HttpServletResponse response){
    	response.setContentType("text/html;charset=utf-8");
    	String filePath = request.getParameter("fileUrl");
    	String fileName = request.getParameter("fileName");
    	String userId = request.getParameter("userId");
    	String realPath = request.getServletContext().getRealPath("/resources/formated/");
    	String targetPath =realPath + "/"+"formated_"+fileName;
    	String result= filter(filePath,targetPath);
    	PrintWriter out;
    	try{
			out = response.getWriter();
        	if(result.equals("格式化成功")){
    	    	int userID = Integer.valueOf(userId).intValue();
				insertFileInfo(realPath,"F","formated_"+fileName,userID);
        	}
        	out.write(result);
        	out.close() ;
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * 查询目标文件
     * @param userId
     * @param fileType
     * @return
     */
    private JSONArray queryTargetFile(String userId,String fileType){
    	PreparedStatement queryStatment = null;
    	Connection conn = connectMysql();
    	ResultSet rs =null;
    	JSONArray json=null;
    	try {
			queryStatment = conn.prepareStatement("select file_name,url from file where user_id = ? and file_type= ? ORDER BY update_time DESC ");
			queryStatment.setString(1,userId);
			queryStatment.setString(2, fileType);
	    	rs = queryStatment.executeQuery();
	    	List<FileInfo> list = new ArrayList<FileInfo>();
	    	FileInfo file = null;
	    	while(rs.next()){
	    		String fileName = rs.getString("file_name");
	    		String url = rs.getString("url");
	    		file = new FileInfo();
	    		file.setFileName(fileName);
	    		file.setUrl(url);
	    		list.add(file);
	    	}
	    	json = JSONArray.fromObject(list);
	    	rs.close();
	    	queryStatment.close();
	    	conn.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return json;
    }

	/**
	 * 查询所有已存在模型
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryAllModel")
	public void queryAllModel(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象
		String userId = request.getParameter("user_id");
		try {
			PrintWriter out = response.getWriter();
			out = response.getWriter();
			JSONArray json = queryTargetFile(userId,"D");
			out.write(json.toString());
			out.flush();
			out.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 查询所有格式化为文件
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryAllFormatedFile")
	public void queryAllFormatedFile(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象
		String userId = request.getParameter("user_id");
		try {
			PrintWriter out = response.getWriter();
			out = response.getWriter();
			JSONArray json = queryTargetFile(userId,"F");
			out.write(json.toString());
			out.flush();
			out.close();
		}  catch(Exception e){
			e.printStackTrace();
		}
	}

    /**
     * 查询所有标注好的文件
     * @param request
     * @param response
     */
    @RequestMapping("/queryAllMarkedFile")
    public void queryAllMarkedFile(HttpServletRequest request,HttpServletResponse response){
    	response.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象
		String userId = request.getParameter("user_id");
    	PrintWriter out;
		try {
			out = response.getWriter();
			JSONArray json = queryTargetFile(userId,"M");
	    	out.write(json.toString());  
	        out.flush();  
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * 查询所有待训练文件
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryAllTrainFile")
	public void queryAllTrainFile(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象
		String userId = request.getParameter("user_id");
		PrintWriter out;
		try {
			out = response.getWriter();
			JSONArray json = queryTargetFile(userId,"A");
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询所有待测试文件
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryAllTestFile")
	public void queryAllTestFile(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象
		String userId = request.getParameter("user_id");
		PrintWriter out;
		try {
			out = response.getWriter();
			JSONArray json = queryTargetFile(userId,"E");
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询所有模板文件
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryAllTemplateFile")
	public void queryAllTemplateFile(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象
		String userId = request.getParameter("user_id");
		PrintWriter out;
		try {
			out = response.getWriter();
			JSONArray json = queryTargetFile(userId,"T");
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询所有待标记文件
	 * @param request
	 * @param response
	 */
    @RequestMapping("/queryAllOrgFile")
    public void queryAllOrgFile(HttpServletRequest request,HttpServletResponse response){
    	response.setContentType("text/html;charset=utf-8");
    	String userId= request.getParameter("user_id");
    	String fileType = request.getParameter("file_type");
    	PrintWriter out;
		try {
			out = response.getWriter();
			JSONArray json = queryTargetFile(userId,fileType);
	    	out.write(json.toString());  
	        out.flush();  
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * 生成交叉验证的数据（分为训练数据和测试数据）
	 * @param response
	 * @param request
	 */
    @RequestMapping("/produceCrossedData")
	public void produceCrossedData(HttpServletResponse response,HttpServletRequest request){
    	String filePath = request.getParameter("fileUrl");
    	String splitNum = request.getParameter("crossedVal");
    	String fileName = request.getParameter("fileName");
    	String userId = request.getParameter("userId");
		response.setContentType("text/html;charset=utf-8");
		try{
			PrintWriter out = response.getWriter();
			File file=new File(filePath);
			if(file.isFile() && file.exists()){
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");//考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt;
				int count=0;
				while((lineTxt = bufferedReader.readLine()) != null){
					count++;
				}
				int splitPart = Integer.parseInt(splitNum); //将格式化的文件划分的块数
				int splitPosition = (int)Math.ceil(count*(splitPart-1)/splitPart);
				read = new InputStreamReader(new FileInputStream(file));
				BufferedReader br = new BufferedReader(read);
				String realPath = request.getServletContext().getRealPath("/resources/trainAndTest/");
				String targetTarinPath =realPath + "/"+"train_"+fileName;
				FileOutputStream fos = new FileOutputStream(targetTarinPath);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
				int curser = 0;
				while ((lineTxt = br.readLine()) != null){
					osw.write(lineTxt+"\n");
					osw.flush();
					curser ++;
					if(curser >= splitPosition){
						if(lineTxt.equals("")){
							break;
						}
					}
				}
				int userID = Integer.valueOf(userId).intValue();
				insertFileInfo(realPath,"A","train_"+fileName,userID);
				realPath = request.getServletContext().getRealPath("/resources/trainAndTest/");
				String targetTestPath = realPath + "/" + "test_" + fileName;
				FileWriter fwTest = new FileWriter(targetTestPath,false);
				fos = new FileOutputStream(targetTestPath);
				osw = new OutputStreamWriter(fos,"UTF-8");
				while ((lineTxt = br.readLine()) != null){
					osw.write(lineTxt+"\n");
					osw.flush();
				}
				insertFileInfo(realPath,"E","test_"+fileName,userID);
				osw.close();
				out.write("操作成功！");
			}else{
				System.out.println("找不到指定的文件");
				out.write("找不到指定文件！");
			}
			out.close();
		}catch (Exception e){
    		e.printStackTrace();
		}
	}
    /**
     * 将标注好的数据保存到服务器
     * @param request
     * @param response
     * @return
     * @throws FileNotFoundException
     */
    @RequestMapping("/saveServer")
    public void saveServer(HttpServletRequest request,HttpServletResponse response){
    	String realPath = request.getServletContext().getRealPath("/resources/marked/");
    	String targetPath = realPath + "/"+"marked_"+request.getParameter("fileName");
    	String userId = request.getParameter("userId");
    	try {
			FileOutputStream fos = new FileOutputStream(targetPath);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			String str = request.getParameter("data");
			osw.write(str);
			osw.flush();
			int userID = Integer.valueOf(userId).intValue();
	    	insertFileInfo(targetPath,"M","marked_"+request.getParameter("fileName"),userID);
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 获取上传文件的内容,传送到前端页面
     * @param request
     * @param response
     */
    @RequestMapping("/getText")
    public void getText(HttpServletRequest request,HttpServletResponse response){
    	 FileInputStream fis = null;  
         OutputStream os = null; 
         File file = null;
         response.setContentType("text/html;charset=utf-8");
         try {  
        	 file = new File(request.getParameter("file_url"));
             fis = new FileInputStream(file);  
             os = response.getOutputStream();  
             int count = 0;  
             byte[] buffer = new byte[1024 * 8];  
             while ((count = fis.read(buffer)) != -1) {  
                 os.write(buffer, 0, count);  
                 os.flush();  
             }  
         } catch (Exception e) {  
             e.printStackTrace();  
         }  
         try {  
             fis.close();  
             os.close();  
         } catch (IOException e) {  
             e.printStackTrace();  
         }  
    }
    /**
     * 上传文件
     * @param file
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="/fileUpload", method = RequestMethod.POST)
    public void fileUpLoad(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String fileType = new String( request.getParameter("file_type").getBytes("ISO-8859-1"),"utf-8");
		String userId = "45";
		String realPath = null;
    	String fileFlag = null;
    	if(fileType.equals("待标记文件")){
    		realPath = request.getServletContext().getRealPath("/resources/needMark/");
    		fileFlag = "O";
    	}else if(fileType.equals("训练模板")){
    		realPath = request.getServletContext().getRealPath("/resources/trainAndTest/");
    		fileFlag = "T";
    	}else if(fileType.equals("可用模型")){
    		realPath = request.getServletContext().getRealPath("/resources/model/");
    		fileFlag = "D";
    	}else{
    		realPath = request.getServletContext().getRealPath("/resources/trainAndTest/");
    		fileFlag = "E";
    	}
		String fileName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"),"utf-8");
    	File filePath =new File(realPath);
    	if  (!filePath.exists()  && !filePath.isDirectory())      
    	{
    	    filePath .mkdir();    
    	} 
    	if(!file.isEmpty()){
    		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath+"/",fileName));
    		int userID = Integer.valueOf(userId).intValue();
    		insertFileInfo(realPath,fileFlag,fileName,userID);
		}
    }
    /**
     * 将插入文件的信息插入数据库，如果存在， 则更新一下时间
     * @param filePath
     * @param fileFlag
     * @param fileName
     */
    private void insertFileInfo(String filePath,String fileFlag,String fileName,int userId){
    	PreparedStatement insertStatement = null;
    	PreparedStatement queryStatment = null;
    	PreparedStatement updateStatment = null;
    	Connection conn = connectMysql();
    	ResultSet rs = null;
		try {
			queryStatment = conn.prepareStatement("select count(*) record from file where file_name = ? and file_type = ?");
			queryStatment.setString(1,fileName);
			queryStatment.setString(2,fileFlag);
			rs = queryStatment.executeQuery();
			int row = 0;
			while(rs.next()){
	    		row = rs.getInt("record");
	    	}
			Date time = new Date();
            SimpleDateFormat timesf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            String updatetime = timesf.format(time);
			if( row >0){
				updateStatment = conn.prepareStatement("update file set update_time = ? where file_type = ? and file_name = ?");
				updateStatment.setString(1, updatetime);
				updateStatment.setString(2, fileFlag);
				updateStatment.setString(3, fileName);
				updateStatment.executeUpdate();
				conn.commit();
				updateStatment.close();
	            conn.close();
			}else {
				insertStatement  = conn.prepareStatement("insert into " + "file" +   
						"(user_id,url,file_name,update_time,file_type) values (?,?,?,?,?)");
				insertStatement.setInt(1, userId);
	        	insertStatement.setString(2, filePath+"/"+fileName);
	        	insertStatement.setString(3, fileName);
                insertStatement.setString(4, updatetime);
                insertStatement.setString(5, fileFlag);
	        	insertStatement.executeUpdate();  
	            conn.commit();
	            insertStatement.close();
	            conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    /**
     * 查询所有已存在未标记文件文件
     * @param req
     * @param rep
     */
    @RequestMapping("/queryAllFile")
    public void queryAllFile(HttpServletRequest req,HttpServletResponse rep){
    	rep.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象  
    	PreparedStatement queryStatment = null;
    	Connection conn = connectMysql();
    	ResultSet rs =null;
    	try {
			queryStatment = conn.prepareStatement("select file_name,url from file where user_id = ? ORDER BY update_time DESC ");
			queryStatment.setString(1,req.getParameter("user_id"));
	    	rs = queryStatment.executeQuery();
	    	PrintWriter out = rep.getWriter();
	    	List<FileInfo> list = new ArrayList<FileInfo>();
	    	FileInfo file = null;
	    	while(rs.next()){
	    		String fileName = rs.getString("file_name");
	    		String url = rs.getString("url");
	    		file = new FileInfo();
	    		file.setFileName(fileName);
	    		file.setUrl(url);
	    		list.add(file);
	    	}
	    	JSONArray json = JSONArray.fromObject(list);
	    	out.write(json.toString());  
	        out.flush();  
	        out.close(); 
	    	rs.close();
	    	queryStatment.close();
	    	conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
    }
    
    /**
     * 连接数据库
     * @return 数据库
     */
    public Connection connectMysql(){
    	try {  
            //调用Class.forName()方法加载驱动程序  
            Class.forName("com.mysql.jdbc.Driver");  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } 
    	String hostname = "localhost";
    	String port = "3306";
    	String databaseName = "mark_system";
    	String url = "jdbc:mysql://" + hostname + ":" + port + "/" + databaseName;
    	String user = "root";
    	String password = "root";
    	Connection conn = null; 
    	try {  
            conn = DriverManager.getConnection(url, user, password);  
            conn.setAutoCommit(false); 

        } catch (SQLException e) {  
            e.printStackTrace();  
            System.exit(1);  
        }  
        return conn;
    }

	public static Map<String,String> map = new HashMap<String,String>();
	static {
		map.put("主谓关系", "SBV");
		map.put("动宾关系", "VOB");
		map.put("间宾关系", "IOB");
		map.put("前置宾语", "FOB");
		map.put("兼语", "DBL");
		map.put("定中关系", "ATT");
		map.put("状中结构", "ADV");
		map.put("动补结构", "CMP");
		map.put("并列关系", "COO");
		map.put("介宾关系", "POB");
		map.put("左附加关系", "LAD");
		map.put("右附加关系", "RAD");
		map.put("独立结构", "IS");
		map.put("核心关系", "HED");
		map.put("标点符号","BDF");
	}

	/**
	 * 注册用户
	 * @param request
	 * @param response
	 */
	@RequestMapping("/registerUser")
	public void registerUser(HttpServletRequest request, HttpServletResponse response){
		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");
		String userPhone = request.getParameter("user_phone");
		String userEmail = request.getParameter("user_email");
		response.setContentType("text/html;charset=utf-8");
		PreparedStatement insertStatement = null;
		PreparedStatement queryStatment = null;
		Connection conn = connectMysql();
		ResultSet rs = null;
		try {
			queryStatment = conn.prepareStatement("select count(*) record from user where user_name = ?");
			queryStatment.setString(1,userName);
			rs = queryStatment.executeQuery();
			PrintWriter out = response.getWriter();
			int row = 0;
			while(rs.next()){
				row = rs.getInt("record");
			}

			if( row >0){
				out.write("用户名已存在");
				out.close();
				conn.close();
			}else {
				insertStatement  = conn.prepareStatement("insert into user (user_name,password,user_phone,user_email) values (?,?,?,?)");
				insertStatement.setString(1, userName);
				insertStatement.setString(2, password);
				insertStatement.setString(3, userPhone);
				insertStatement.setString(4, userEmail);
				insertStatement.executeUpdate();
				conn.commit();
				insertStatement.close();
				queryStatment = conn.prepareStatement("select id from user where user_name = ?");
				queryStatment.setString(1,userName);
				rs = queryStatment.executeQuery();
				int id = 0;
				while (rs.next()){
					id = rs.getInt("id");
				}
				conn.close();
				out.write(Integer.toString(id));
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登录用户
	 * @param request
	 * @param response
	 */
	@RequestMapping("/loginUser")
	public void loginUser(HttpServletRequest request, HttpServletResponse response){
		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");

		response.setContentType("text/html;charset=utf-8");
		PreparedStatement queryStatment = null;
		Connection conn = connectMysql();
		ResultSet rs = null;

		try {
			queryStatment = conn.prepareStatement("select count(*) record, id from user where user_name = ? and password = ?");
			queryStatment.setString(1,userName);
			queryStatment.setString(2,password);
			rs = queryStatment.executeQuery();
			PrintWriter out = response.getWriter();
			int row = 0;
			int id = 0;
			while(rs.next()){
				row = rs.getInt("record");
				id  = rs.getInt("id");
			}
			if( row >0){
				out.write(Integer.toString(id));
				out.close();
				conn.close();
			}else {

				conn.close();
				out.write("用户名或密码错误");
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回用户信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryUserInfo")
	public void queryUserInfo(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userId = request.getParameter("user_id");
		PreparedStatement queryUserInfo = null;
		Connection conn = connectMysql();
		ResultSet rs = null;
		try {
			queryUserInfo = conn.prepareStatement("select user_phone,user_email,user_name,update_time from user where id = ?");
			queryUserInfo.setString(1,userId);
			rs = queryUserInfo.executeQuery();
			PrintWriter out = response.getWriter();
			List<User> list = new ArrayList<User>();
			User user = new User();
			while(rs.next()){
				String userName = rs.getString("user_name");
				String userPhone = rs.getString("user_phone");
				String userEmail = rs.getString("user_email");
				String updateTime = rs.getString("update_time");

				user.setUserPhone(userPhone);
				user.setUserName(userName);
				user.setUserEmail(userEmail);
				user.setUpdateTime(updateTime);
			}
			queryUserInfo = conn.prepareStatement("select count(*) fileSum from file where user_id = ?");
			queryUserInfo.setString(1,userId);
			rs = queryUserInfo.executeQuery();
			while (rs.next()){
				int  fileSum = rs.getInt("fileSum");
				user.setFileSum(Integer.toString(fileSum));
			}
			list.add(user);
			JSONArray json = JSONArray.fromObject(list);
			out.write(json.toString());
			out.flush();
			out.close();
			rs.close();
			queryUserInfo.close();
			conn.close();
		}  catch(Exception e){
			e.printStackTrace();
		}
	}

	@RequestMapping("/updateUserInfo")
	public void updateUserInfo(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userId = request.getParameter("user_id");
		String userPhone = request.getParameter("user_phone");
		String userEmail = request.getParameter("user_email");
		PreparedStatement updateUserInfo = null;
		Connection conn = connectMysql();
		try {
			updateUserInfo = conn.prepareStatement("update user set user_phone = ? , user_email = ? where id = ?");
			updateUserInfo.setString(1,userPhone);
			updateUserInfo.setString(2,userEmail);
			updateUserInfo.setString(3,userId);
			int result = updateUserInfo.executeUpdate();

			PrintWriter out = response.getWriter();
			if ( result > 0){
				out.write("更新成功");
			}else{
				out.write("更新失败，请重试");
			}
			conn.commit();
			out.flush();
			out.close();
			updateUserInfo.close();
			conn.close();
		}  catch(Exception e){
			e.printStackTrace();
		}
	}
}
