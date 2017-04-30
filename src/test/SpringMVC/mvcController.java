package test.SpringMVC;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
import java.util.List;
import java.util.Map;

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
	
	//ajax 返回请求结果
	@RequestMapping("/getText")
	public void getText(HttpServletRequest request,HttpServletResponse response)throws IOException{
		
		String str="This is a test";
		response.getWriter().print(str); 
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
    
    @RequestMapping("/getManageMark")
    public String getManageMark(){
    	return "manageMark";
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
    	String fileName =file.getOriginalFilename();
    	File filePath =new File(realPath);   
    	
    	//如果文件夹不存在则创建    
    	if  (!filePath.exists()  && !filePath.isDirectory())      
    	{        
    	    filePath .mkdir();    
    	} 
    	if(!file.isEmpty()){
    		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath+"\\",fileName));
    		PreparedStatement insertStatement = null;
        	Connection conn = connectMysql();
        	try {
        		insertStatement  = conn.prepareStatement("insert into " + "file" +   
						"(user_id,url,file_name,update_time) values (?,?,?,?)");
				insertStatement.setString(1, "45");
	        	insertStatement.setString(2, realPath+"\\"+fileName);
	        	insertStatement.setString(3, fileName);
	        	Date time = new Date();
                SimpleDateFormat timesf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                String updatetime = timesf.format(time);
                insertStatement.setString(4,updatetime);
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
     * 查询所有已存在文件
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
			queryStatment = conn.prepareStatement("select file_name from file where user_id = ? ORDER BY update_time DESC ");
			queryStatment.setString(1,req.getParameter("user_id"));
	    	rs = queryStatment.executeQuery();
	    	PrintWriter out = rep.getWriter();
	    	List<FileInfo> list = new ArrayList<FileInfo>();
	    	FileInfo file = null;
	    	while(rs.next()){
	    		String fileName = rs.getString("file_name");
	    		file = new FileInfo();
	    		file.setFileName(fileName);
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
