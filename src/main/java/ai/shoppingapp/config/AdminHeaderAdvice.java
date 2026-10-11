package ai.shoppingapp.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.model.usermanagement.ChangeProfileModel;
import ai.shoppingapp.service.UserService;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class AdminHeaderAdvice {

    private final UserService userService;

    public AdminHeaderAdvice(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addAdminProfile(Model model, HttpSession session) {

        UserModel loggedInUser =
                (UserModel) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {

            UserModel currentUser =
                    userService.findById(loggedInUser.getId());

            if (currentUser != null) {

                ChangeProfileModel profile =
                        new ChangeProfileModel();

                profile.setId(currentUser.getId());
                profile.setName(currentUser.getName());
                profile.setEmail(currentUser.getEmail());
                profile.setPhone(currentUser.getPhone());
                profile.setAddress(currentUser.getAddress());
                profile.setProfile(currentUser.getProfile());

                model.addAttribute("changeProfileModel", profile);
            }
        }
    }
}