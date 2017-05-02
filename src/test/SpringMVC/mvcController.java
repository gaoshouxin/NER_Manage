package test.SpringMVC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONArray;
import test.SpringMVC.model.FileInfo;
import test.SpringMVC.model.Person;
import test.SpringMVC.model.Project;

@Controller
@RequestMapping("/mvc")
public class mvcController {
	@RequestMapping("/hello")
    public String hello(){
        return "success";
    }
	
	@RequestMapping("/distinguish")
    public String distinguish(){        
        return "distinguish";
    }
	
	//match automatically
    @RequestMapping("/person")
    public String toPerson(String name,double age){
        System.out.println(name+" "+age);
        return "hello";
    }
    
	//boxing automatically
    @RequestMapping("/person1")
    public String toPerson(Person p){
        System.out.println(p.getName()+" "+p.getAge());
        return "hello";
    }
    
  //the parameter was converted in initBinder
    @RequestMapping("/date")
    public String date(Date date){
        System.out.println(date);
        return "hello";
    }
    
    //At the time of initialization,convert the type "String" to type "date"
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),
                true));
    }
    
  //pass the parameters to front-end
    @RequestMapping("/show")
    public String showPerson(Map<String,Object> map){
        Person p =new Person();
        map.put("p", p);
        p.setAge(20);
        p.setName("jayjay");
        return "show";
    }
    
  //pass the parameters to front-end using ajax
    @RequestMapping("/getPerson")
    public void getPerson(String name,PrintWriter pw){
        pw.write("hello,"+"SAASAASA");        
    }
    
    /**
     * 
     * @return
     */
    @RequestMapping("/name")
    public String sayHello(){
        return "name";
    }
    
    /**
     * 跳转到标记页面
     * @return
     */
    @RequestMapping("/getMark")
    public String getMark(){
    	return "mark";
    }
    
    @RequestMapping("/getText_1")
    public static void getText_1(HttpServletRequest req,HttpServletResponse response){
    	String realpath = req.getSession().getServletContext().getRealPath("/resources/crf"); 
    	PrintWriter out;
		Process p;
		try {
			p = Runtime.getRuntime().exec("crf_test -m /home/cz/model /home/cz/learn.txt");
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			out = response.getWriter();
			while ((line = br.readLine()) != null) {
				if(line.length() < 1){
					System.out.println("\n");
					sb.append("\n");
					continue;
				}
				System.out.println(line);
				out.write(line+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public static void main(String[] args) {
//    	System.out.println("hello");
//    	String commandStr = "crf_learn -c 8 -p 14 template_baseline learn.txt model";
    	String commandStr = "crf_test -m model learn.txt";
//    	String commandStr = "ping www.baidu.com";
    	Process p;
		try {
			p = Runtime.getRuntime().exec(commandStr);
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				if(line.length() < 1){
					System.out.println("\n");
					sb.append("\n");
					continue;
				}
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    @RequestMapping("/getWelcome")
    public String getWelcome(){
    	return "welcome";
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
    
    @RequestMapping("/train")
    public void train(HttpServletRequest request,HttpServletResponse response){
    	String realPath = request.getServletContext().getRealPath("/resources/formated/");
    	String fileName=request.getParameter("fileName");
    	System.out.println(realPath);
    	response.setContentType("text/html;charset=utf-8");
    	PrintWriter out;
    	String cmdString=realPath+"/crf_test -m model learn.txt";
    	
		Process p;
		try {
			p = Runtime.getRuntime().exec("crf_test -m /home/cz/model /home/cz/learn.txt");
			BufferedReader br  = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			out = response.getWriter();
			while ((line = br.readLine()) != null) {
				if(line.length() < 1){
					System.out.println("\n");
					sb.append("\n");
					continue ;
				}
				System.out.println(line);
				out.write(line+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * 查询所有格式化为文件
     * @param req
     * @param rep
     */
    @RequestMapping("/queryAllFormatedFile")
    public void queryAllFormatedFile(HttpServletRequest req,HttpServletResponse rep){
    	rep.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象  
    	PreparedStatement queryStatment = null;
    	Connection conn = connectMysql();
    	ResultSet rs =null;
    	try {
			queryStatment = conn.prepareStatement("select file_name,url from file where user_id = ? and file_type= ? ORDER BY update_time DESC ");
			queryStatment.setString(1,req.getParameter("user_id"));
			queryStatment.setString(2, "F");
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
                Queue<Character> queue = new LinkedList<Character>();
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
                	int i=0;
                	while(i<lineTxt.length()){
                		if('#'== lineTxt.charAt(i) && startTag){
                			i++;
                			startTag = false;
                		}else if('#' != lineTxt.charAt(i) && (!startTag)){
                			queue.offer(lineTxt.charAt(i));
                			hasQueue = true;
                			i++;
                		}else if('#' != lineTxt.charAt(i)&& startTag){
                			fw.write(lineTxt.charAt(i)+"\t"+"O\n");
                			i++;
                		}else if('#' == lineTxt.charAt(i)&& (!startTag)){
                			startTag = true;
                			if(hasQueue){//一定不为空
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
                			i++;
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
    	String realPath = request.getServletContext().getRealPath("/resources/formated/");
    	String targetPath =realPath + "/"+"formated_"+fileName;
    	System.out.println(filePath);
    	String result= filter(filePath,targetPath);
    	PreparedStatement insertStatement = null;
    	Connection conn = connectMysql();
    	PrintWriter out;
    	try{
    		 out = response.getWriter();
        	if(result.equals("格式化成功")){
        		
    	    	//保存数据库
    	    	insertStatement  = conn.prepareStatement("insert into " + "file" +   
    					"(user_id,url,file_name,update_time,file_type) values (?,?,?,?,?)");
    			insertStatement.setString(1, "45");
            	insertStatement.setString(2, targetPath);
            	insertStatement.setString(3, "formated_"+request.getParameter("fileName"));
            	Date time = new Date();
                SimpleDateFormat timesf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                String updatetime = timesf.format(time);
                insertStatement.setString(4,updatetime);
            	insertStatement.setString(5, "F");//格式化的文件
                insertStatement.executeUpdate();  
                conn.commit();
                insertStatement.close();
                conn.close();
        	}
        	out.write(result);
        	out.close() ;
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * 返回所有标注好的文件
     * @param req
     * @param rep
     */
    @RequestMapping("/queryAllMarkedFile")
    public void queryAllMarkedFile(HttpServletRequest req,HttpServletResponse rep){
    	rep.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象  
    	PreparedStatement queryStatment = null;
    	Connection conn = connectMysql();
    	ResultSet rs =null;
    	try {
			queryStatment = conn.prepareStatement("select file_name,url from file where user_id = ? and file_type= ? ORDER BY update_time DESC ");
			queryStatment.setString(1,req.getParameter("user_id"));
			queryStatment.setString(2, "M");
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
     * 将标注好的数据保存到服务器
     * @param request
     * @param response
     * @return
     * @throws FileNotFoundException
     */
    @RequestMapping("/saveServer")
    public String saveServer(HttpServletRequest request,HttpServletResponse response){
    	String realPath = request.getServletContext().getRealPath("/resources/marked/");
    	String targetPath =realPath + "/"+"marked_"+request.getParameter("fileName");
    	File file =new File(targetPath);  
    	OutputStream out = null ; // 准备好一个输出的对象
    	PreparedStatement insertStatement = null;
    	Connection conn = connectMysql();
    	try {
			out = new FileOutputStream(file)  ;
			String str = request.getParameter("data");  
	    	byte b[] = str.getBytes() ;   // 只能输出byte数组，所以将字符串变为byte数组
	    	out.write(b) ;      // 将内容输出，
	    	//保存数据库
	    	insertStatement  = conn.prepareStatement("insert into " + "file" +   
					"(user_id,url,file_name,update_time,file_type) values (?,?,?,?,?)");
			insertStatement.setString(1, "45");
        	insertStatement.setString(2, targetPath);
        	insertStatement.setString(3, "marked_"+request.getParameter("fileName"));
        	Date time = new Date();
            SimpleDateFormat timesf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            String updatetime = timesf.format(time);
            insertStatement.setString(4,updatetime);
        	insertStatement.setString(5, "M");
            insertStatement.executeUpdate();  
            conn.commit();
            insertStatement.close();
            conn.close();
	    	
	    	out.close() ;      // 关闭输出流
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		}
    	return "identification";
    }
    
    /**
     * 获取上传文件的内容
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
    public void fileUpLoad(@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws IOException{ 
    	String realPath = request.getServletContext().getRealPath("/resources/needMark/");
    	System.out.println(realPath);
    	String fileName =file.getOriginalFilename();
    	File filePath =new File(realPath);   
    	
    	//如果文件夹不存在则创建    
    	if  (!filePath.exists()  && !filePath.isDirectory())      
    	{ 
    		System.out.println("文件夹不存在");
    	    filePath .mkdir();    
    	} 
    	if(!file.isEmpty()){
    		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath+"/",fileName));
    		PreparedStatement insertStatement = null;
        	Connection conn = connectMysql();
        	try {
        		insertStatement  = conn.prepareStatement("insert into " + "file" +   
						"(user_id,url,file_name,update_time,file_type) values (?,?,?,?,?)");
				insertStatement.setString(1, "45");
	        	insertStatement.setString(2, realPath+"/"+fileName);
	        	insertStatement.setString(3, fileName);
	        	Date time = new Date();
                SimpleDateFormat timesf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                String updatetime = timesf.format(time);
                insertStatement.setString(4,updatetime);
                insertStatement.setString(5, "O");
	        	insertStatement.executeUpdate();  
	            conn.commit();
	            insertStatement.close();
	            conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			queryStatment = conn.prepareStatement("select file_name,url from file where user_id = ? and file_type= ? ORDER BY update_time DESC ");
			queryStatment.setString(1,req.getParameter("user_id"));
			queryStatment.setString(2, "O");
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
     * 查询返回所有已存在项目
     * @param req
     * @param pw
     */
    @RequestMapping("/queryAllProject")
    public void queryAllProject(HttpServletRequest req,HttpServletResponse rep){
    	rep.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象  
    	PreparedStatement queryStatment = null;
    	Connection conn = connectMysql();
    	ResultSet rs =null;
    	try {
			queryStatment = conn.prepareStatement("select pro_name from project where user_id = ? ORDER BY update_time DESC ");
			queryStatment.setString(1,req.getParameter("user_id"));
	    	rs = queryStatment.executeQuery();
	    	PrintWriter out = rep.getWriter();
	    	List<Project> list = new ArrayList<Project>();
	    	Project pro = null;
	    	while(rs.next()){
	    		String proName = rs.getString("pro_name");
	    		pro = new Project();
	    		pro.setProName(proName);
	    		list.add(pro);
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
     * 创建项目，遇到重复时会反馈
     * @param req
     * @param pw
     */
    @RequestMapping("/createProject")
    public void createProject(HttpServletRequest req,PrintWriter pw){
    	PreparedStatement insertStatement = null;
    	PreparedStatement queryStatment = null;
    	Connection conn = connectMysql();
    	ResultSet rs =null;
    	boolean isInserted;
    	//创建一个Statement对象  
        try {
        	queryStatment = conn.prepareStatement("select count(*) record from project where pro_name = ?");
        	queryStatment.setString(1,req.getParameter("project_name"));
        	rs = queryStatment.executeQuery();
        	isInserted = false;
        	while(rs.next()){
        		int rowNum = rs.getInt("record");
        		if(rowNum < 1){
        			isInserted = true;
        			insertStatement = conn.prepareStatement("insert into " + "project" +   
        					" (user_id,pro_name,pro_sign,update_time) values (?,?,?,?)");
		        	insertStatement.setString(1, "45");
		        	insertStatement.setString(2, req.getParameter("project_name"));
		        	insertStatement.setString(3, req.getParameter("mark_sign"));
		        	Date time = new Date();
                    SimpleDateFormat timesf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    String updatetime = timesf.format(time);
                    insertStatement.setString(4,updatetime);
		        	insertStatement.executeUpdate();  
		            conn.commit();
		            pw.write("1");
        		}else{
        			pw.write("0");
        		}
        	}
        	//关闭连接，与之前的操作反着来
        	rs.close();
        	if(isInserted){
        		insertStatement.close();
        	}else{
        		queryStatment.close();
        	}
        	conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
    }
    
    /**
     * 连接数据库
     * @return 数据库
     */
    private Connection connectMysql(){
    	try {  
            //调用Class.forName()方法加载驱动程序  
            Class.forName("com.mysql.jdbc.Driver");  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } 
    	String hostname = "115.159.40.100";
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
}
