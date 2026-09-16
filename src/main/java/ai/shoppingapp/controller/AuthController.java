package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.model.usermanagement.LoginModel;
import ai.shoppingapp.model.usermanagement.RegisterModel;
import ai.shoppingapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService=userService;
	}
	@GetMapping("/login")
    public String loginPage(Model model) {
		model.addAttribute("user",new LoginModel());
        return "users/login";
    }
	@PostMapping("/login")
    public String login(@Valid@ModelAttribute("user")LoginModel user,
    		BindingResult bindingResult,
            HttpSession session,
            HttpServletRequest request,
            Model model) {
		if(bindingResult.hasErrors()) {						
			return "users/login";
		}
		
        UserModel loginUser = userService.login(user);

        if (loginUser != null) {
        	session.invalidate(); // prevent session fixation
        	HttpSession newSession = request.getSession(true);
        	newSession.setAttribute("loggedInUser", loginUser);
        	return "redirect:/";
        }

        model.addAttribute("error","Invalid email or password");
        return "users/login";
    }
	@GetMapping("/register")
    public String registerPage(Model model) {
		model.addAttribute("user",new RegisterModel());
        return "users/register";
    }
	@PostMapping("/register")
    public String register(@Valid @ModelAttribute("user")RegisterModel registerUser,
    		BindingResult bindingResult,
            Model model) {
		if(bindingResult.hasErrors()) {						
			return "users/register";
		}
		int isSuccess=userService.register(registerUser);
		if(isSuccess!=1) {
			model.addAttribute("error", "Email already exists");
            return "users/register";
		}
        return "redirect:/login";
    }
	@GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
	@GetMapping("/access-denied")
    public String accessDenied(Model model) {
		
        return "access-denied";
    }
}
