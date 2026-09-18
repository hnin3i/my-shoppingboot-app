package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@GetMapping("/errors/not-authorized")
	public String notAuthorized() {
	    return "errors/not-authorized";
	}
	
}