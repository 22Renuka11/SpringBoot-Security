package SpringSecurity.example.SpringSecurity.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import SpringSecurity.example.SpringSecurity.Model.JobPost;
import SpringSecurity.example.SpringSecurity.Model.UserLogin;
import SpringSecurity.example.SpringSecurity.SecurityService.JWTService;
import SpringSecurity.example.SpringSecurity.SecurityService.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class Controller {
    @Autowired
    SecurityService securityService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService JWTService;
    
    @GetMapping("Hello")
    public String getHello(HttpSession session) {
        return "Hello "+ session.getId();
    }
    @GetMapping("UserLoginList")
    public List<UserLogin> UserLoginDetailsList(){
        return securityService.UserLoginDetailsList();
    }
    @PostMapping("Register")
    public String Register(@RequestBody UserLogin register){
        securityService.RegisterData(register);
        return "Registered Successfully";
    }
    @PostMapping("Login")
    public String Login(@RequestBody UserLogin login){
       Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
       if(authentication.isAuthenticated()){
        return JWTService.login(login.getUserName());
       }
       return "Login Failed";
    }
    @GetMapping("About")
    public String getMethodName(HttpSession session) {
        return "About "+ session.getId();
    }
    List<JobPost> jobs = 
					new ArrayList<>(List.of(
							new JobPost(1, "Software Engineer", "Exciting opportunity for a skilled software engineer.", 3, List.of("Java", "Spring", "SQL")),
							new JobPost(2, "Data Scientist", "Join our data science team and work on cutting-edge projects.", 5, List.of("Python", "Machine Learning", "TensorFlow")),
							 new JobPost(3, "Frontend Developer", "Create amazing user interfaces with our talented frontend team.", 2, List.of("JavaScript", "React", "CSS")),
							 new JobPost(4, "Network Engineer", "Design and maintain our robust network infrastructure.", 4, List.of("Cisco", "Routing", "Firewalls")),
							 new JobPost(5, "UX Designer", "Shape the user experience with your creative design skills.", 3, List.of("UI/UX Design", "Adobe XD", "Prototyping"))

					));
    @GetMapping("Jobs")
    public List<JobPost> getJobs() {
        return jobs;
    }
    
    @GetMapping("UserDetails")
    public List<UserLogin> UserDetais(){
        return securityService.UserDetails();
    }
    @GetMapping("csrfToken")
    public CsrfToken getCSRFToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
    @PostMapping("AddJob")
    public List<JobPost> AddJob(@RequestBody JobPost job) {        
        jobs.add(job);
        return jobs;
    }
    @PutMapping("UpdateJob/{JobPost}")
    public List<JobPost> putMethodName(@PathVariable int JobPost, @RequestBody JobPost entity) { 
        for (JobPost job : jobs) {
            if(job.getPostId()==JobPost){
                job.setPostDesc(entity.getPostDesc());
                job.setPostProfile(entity.getPostProfile());
                job.setPostTechStack(entity.getPostTechStack());
                job.setReqExperience(entity.getReqExperience());
            }
        }       
        return jobs;
    }
    
}
