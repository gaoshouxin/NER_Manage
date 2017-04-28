package test.SpringMVC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import test.SpringMVC.model.Person;
import java.lang.Runtime;
import java.net.URLDecoder;

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
    @RequestMapping("/name")
    public String sayHello(){
        return "name";
    }
    
    @RequestMapping("/getText_1")
    public static void getText_1(HttpServletRequest req,HttpServletResponse response){
    	String realpath = req.getSession().getServletContext().getRealPath("/resources/crf"); 
    	System.out.println(req);
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
    
}
