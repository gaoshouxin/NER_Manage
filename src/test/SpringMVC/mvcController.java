package test.SpringMVC;

import java.io.IOException;
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
    
}
