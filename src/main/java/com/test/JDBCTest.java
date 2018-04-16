package com.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Statement;  
@Controller  
public class JDBCTest {  
    @Autowired  
    JdbcTemplate jdbc;    
    @RequestMapping("/insert")  
    public ModelAndView index(String name, String email){  
    	String query = "insert into user(name,email) values('" +name +"','" + email + "')";
    	System.out.println("Query : " + query);
        jdbc.execute(query);
        return select();  
    }  
    
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public ModelAndView select(){  
    	String sql = "SELECT * FROM user";
		 
    	ArrayList<User> customers = new ArrayList<User>();
	
    	ModelAndView modelAndView = new ModelAndView();
    	List<Map<String, Object>> rows = jdbc.queryForList(sql);
    	for (Map row : rows) {
    		User user = new User();
    		user.setEmail((String)row.get("email"));
    		user.setName((String)row.get("name"));
    		customers.add(user);
    		System.out.println(user.name);
    				
    	}
    	
    	modelAndView.setViewName("user-data");
		modelAndView.addObject("user", customers);
    			
		return  modelAndView;
    }  
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteAll()
    {
    	String query = "delete from user";
    	System.out.println("Query : " + query);
        jdbc.execute(query);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("messages");
		modelAndView.addObject("message", "All items deleted");
        return modelAndView;
    }
}  
