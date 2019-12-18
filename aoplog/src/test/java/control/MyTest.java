package control;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yuanjun.service.PersonService;



public class MyTest {
	
	 @Test  
    public void test() {  
        AbstractApplicationContext aContext = //  
        new ClassPathXmlApplicationContext( new String[]{"classpath:spring-mybatis.xml",
        		"classpath:spring-mvc.xml"});  
        PersonService pService = (PersonService) aContext  
                .getBean("personService");  
        pService.findAll();
    }  
}	
