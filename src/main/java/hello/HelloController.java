package hello;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*")
public class HelloController {
	
	@Autowired
	ResultRepository repo;
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/insert")
    public String insert() {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/remove")
    public String remove() {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/list")
    public String list() {
    	List<Result> result = (List<Result>) repo.findAll();
        return result.toString();
    }
    
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public String verify(@RequestParam(value = "code") String code) {
    	Result r = null;
    	List<Result> rList = repo.findByCode(code);
    	r = rList.isEmpty() ? null : rList.get(0);
        return r!=null ? "Found": "Not Found";
    }
    
    
    @RequestMapping(value = "/verifyTest", method = RequestMethod.GET)
    public String verifyTest(@RequestParam(value = "code") String code) {
    	Result r = null;
    	List<Result> rList = repo.findByCode(code);
    	if(!rList.isEmpty()){
    		r = rList.get(0);
        	r.setTimeUsed(r.getTimeUsed()+1);
        	r = repo.save(r);
    	}
    	
        return r!=null ? "Found": "Not Found";
    }
    
}
