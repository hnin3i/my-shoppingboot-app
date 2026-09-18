package ai.shoppingapp.controller;

import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.model.usermanagement.LoginModel;
import ai.shoppingapp.model.usermanagement.RegisterModel;
import ai.shoppingapp.service.EmailVerificationService;
import ai.shoppingapp.service.PasswordResetService;
import ai.shoppingapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;
    private final EmailVerificationService emailVerificationService;
    private final PasswordResetService passwordResetService;

    public AuthController(UserService userService, EmailVerificationService emailVerificationService, PasswordResetService passwordResetService) {
        this.userService = userService;
        this.emailVerificationService = emailVerificationService;
        this.passwordResetService = passwordResetService;
    }

    // ==========================================
    // LOGIN & LOGOUT
    // ==========================================

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new LoginModel());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") LoginModel user,
                        BindingResult bindingResult,
                        HttpSession session,
                        HttpServletRequest request,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }

        UserModel loginUser = userService.login(user);

        if (loginUser != null) {
            session.invalidate(); // prevent session fixation
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("loggedInUser", loginUser);
            return "redirect:/";
        }

        model.addAttribute("error", "Invalid email or password");
        return "user/login";
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

    // ==========================================
    // REGISTRATION WITH OTP VERIFICATION
    // ==========================================

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new RegisterModel());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") RegisterModel registerUser,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        // Check if email is already taken by an active user
        UserModel existingUser = userService.findByEmail(registerUser.getEmail());
        if (existingUser != null) {
            model.addAttribute("error", "Email already exists");
            return "user/register";
        }

        // Generate cryptographic 6-digit OTP and send verification email
        emailVerificationService.generateAndSendOtp(registerUser);

        // Redirect to OTP entry page
        return "redirect:/verify-otp?email=" + registerUser.getEmail();
    }

    @GetMapping("/verify-otp")
    public String showOtpPage(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "user/verify-otp";
    }

    @PostMapping("/verify-otp")
    public String submitOtp(@RequestParam("email") String email,
                            @RequestParam("otp") String otp,
                            Model model) {
        String result = emailVerificationService.verifyOtpAndCreateUser(email, otp);

        if (!"SUCCESS".equals(result)) {
            model.addAttribute("error", result);
            model.addAttribute("email", email);
            return "user/verify-otp";
        }

        return "redirect:/login?verified=true";
    }
    
    @PostMapping("/verify-otp/resend")
    public String resendOtp(@RequestParam("email") String email, 
                            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        boolean resent = emailVerificationService.resendOtp(email);

        if (!resent) {
            redirectAttributes.addFlashAttribute("error", "Registration session expired. Please register again.");
            return "redirect:/register";
        }

        return "redirect:/verify-otp?email=" + email + "&resent=true";
    }

    // ==========================================
    // FORGOT & RESET PASSWORD WITH OTP
    // ==========================================

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "user/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String requestResetOtp(@RequestParam("email") String email, Model model) {
        UserModel user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "No account found with this email address.");
            return "user/forgot-password";
        }

        passwordResetService.sendForgotPasswordOtp(email); // Updated here
        return "redirect:/reset-password?email=" + email;
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "user/reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("email") String email,
                                       @RequestParam("otp") String otp,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("email", email);
            return "user/reset-password";
        }

        // Call passwordResetService instead of emailVerificationService
        String result = passwordResetService.verifyResetOtpAndChangePassword(email, otp, newPassword);

        if (!"SUCCESS".equals(result)) {
            model.addAttribute("error", result);
            model.addAttribute("email", email);
            return "user/reset-password";
        }

        return "redirect:/login?resetSuccess=true"; // This return was missing
    }

}