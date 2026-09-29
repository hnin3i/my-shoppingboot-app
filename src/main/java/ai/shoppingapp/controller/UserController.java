package ai.shoppingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.model.usermanagement.ChangeProfileModel;
import ai.shoppingapp.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Handles the GET request when loading the page
    @GetMapping("/edit")
    public String showEditProfilePage(HttpSession session, Model model) {
        UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/";
        }

        UserModel currentUser = userService.findById(loggedInUser.getId());
        if (currentUser == null) {
            return "redirect:/";
        }

        ChangeProfileModel changeProfileModel = new ChangeProfileModel();
        changeProfileModel.setId(currentUser.getId());
        changeProfileModel.setName(currentUser.getName());
        changeProfileModel.setEmail(currentUser.getEmail());
        changeProfileModel.setPhone(currentUser.getPhone());
        changeProfileModel.setAddress(currentUser.getAddress());
        changeProfileModel.setProfile(currentUser.getProfile());

        model.addAttribute("changeProfileModel", changeProfileModel);
        return "user/edit";
    }

    // Handles the form submission (POST)
    @PostMapping("/edit")
    public String processEditProfile(@ModelAttribute("changeProfileModel") ChangeProfileModel changeProfileModel,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {

        UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/";
        }

        changeProfileModel.setId(loggedInUser.getId());
        int result = userService.editProfile(changeProfileModel);

        if (result > 0) {
            UserModel updatedUser = userService.findById(loggedInUser.getId());
            session.setAttribute("loggedInUser", updatedUser);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update profile. Please try again.");
        }

        return "redirect:/user/edit";
    
    }
    @GetMapping("/profile")
	public String profile() {
		return "user/profile";
	}

	@GetMapping("/orders")
	public String orders() {
		return "user/orders";
	}
}
