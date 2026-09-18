package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
//	@GetMapping("/error/not-authorized")
//	public String notAuthorized() {
//	    return "error/not-authorized";
//	}
	@GetMapping("/error")
    public String handleDirectErrorAccess(Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("title", "Page Not Found");
        model.addAttribute("message", "The requested resource could not be found.");
        model.addAttribute("showLoginBtn", false);
        return "error/error";
    }
	
}