package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String home(Model m)
	{
		m.addAttribute("title","Home- Smart Contact Manager");
		return "home";
	}
	@GetMapping("/about")
	public String about(Model m)
	{
		m.addAttribute("title","About-Smart Contact Manager");
		return "about";
	}
	@GetMapping("/signup")
	public String sighup(Model m)
	{
		m.addAttribute("title","Register-Smart Contact Manager");
		m.addAttribute("user", new User());
		return "signup";
	}
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1, @RequestParam(value="agreement",defaultValue="false") Boolean agreement, Model m)
	{
		try {
			if(!agreement)
			{
				throw new Exception("You have not agreed the terms and conditions");
			}
			if(result1.hasErrors())
			{
				m.addAttribute("user", user);
				return "signup";
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			
			System.out.println("Agreement= "+agreement);
			System.out.println("User="+user);
			User result = this.userRepository.save(user);
			m.addAttribute("user",new User());
			//session.setAttribute("message", new Message("Successfully Registered ", "alert-success"));
			return "signup";
		}catch(Exception e)
		{
			e.printStackTrace();
			m.addAttribute("user",user);
			//session.setAttribute("message", new Message("Something went wrong"+e.getMessage(), "alert-danger"));
			return "signup";
		}		
	}
	
//	@PostMapping("/do_register")
//	public String registerUser(@ModelAttribute("user") User user, @RequestParam(value="agreement",defaultValue="false") boolean agreement, Model m, HttpSession session)
//	{
//		try {
//			if(!agreement)
//			{
//				throw new Exception("You have not agreed the terms and conditions");
//			}
//			user.setRole("ROLE_USER");
//			user.setEnabled(true);
//			
//			System.out.println("Agreement= "+agreement);
//			System.out.println("User="+user);
//			User result = this.userRepository.save(user);
//			m.addAttribute("user",new User());
//			session.setAttribute("message", new Message("Successfully Registered ", "alert-success"));
//			return "signup";
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			m.addAttribute("user",user);
//			session.setAttribute("message", new Message("Something went wrong"+e.getMessage(), "alert-danger"));
//			return "signup";
//		}		
//	}
	
	/*@GetMapping("/test")
	@ResponseBody
	public String test()
	{
		User user = new User();
		user.setName("Swati Arya");
		user.setEmail("swatia21899@gmail.com");
		userRepository.save(user);
		return "Working";		
	}*/
          
}
