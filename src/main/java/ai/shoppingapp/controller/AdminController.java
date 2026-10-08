package ai.shoppingapp.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ai.shoppingapp.model.AdminDashboardDto;
import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.model.usermanagement.ChangeProfileModel;
import ai.shoppingapp.service.AdminDashboardService;
import ai.shoppingapp.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final AdminDashboardService adminDashboardService;

	public AdminController(UserService userService, AdminDashboardService adminDashboardService) {

		this.userService = userService;
		this.adminDashboardService = adminDashboardService;
	}

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {

		UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}

		  AdminDashboardDto dashboard =
		            adminDashboardService.getDashboardSummary();

		model.addAttribute("dashboard", dashboard);
		 model.addAttribute("activePage", "dashboard");

		return "admin/dashboard/dashboard";
	}

	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.findAll());
		model.addAttribute("activePage", "users");
		return "admin/users/list";
	}

	@GetMapping("/profile")
	public String profile(HttpSession session, Model model) {

		UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}

		UserModel currentUser = userService.findById(loggedInUser.getId());

		if (currentUser == null) {
			return "redirect:/login";
		}

		ChangeProfileModel profile = new ChangeProfileModel();

		profile.setId(currentUser.getId());
		profile.setName(currentUser.getName());
		profile.setEmail(currentUser.getEmail());
		profile.setPhone(currentUser.getPhone());
		profile.setAddress(currentUser.getAddress());
		profile.setProfile(currentUser.getProfile());

		model.addAttribute("changeProfileModel", profile);

		return "admin/profile/profile";
	}

	@PostMapping("/profile/update")
	public String updateProfile(@ModelAttribute("changeProfileModel") ChangeProfileModel changeProfileModel,
			@RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto, HttpSession session,
			RedirectAttributes redirectAttributes) throws IOException {

		UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}

		// Get current user before updating
		UserModel currentUser = userService.findById(loggedInUser.getId());

		if (currentUser == null) {
			return "redirect:/login";
		}

		changeProfileModel.setId(loggedInUser.getId());

		// Keep old profile path
		String oldProfile = currentUser.getProfile();

		// =========================
		// PROFILE PHOTO UPLOAD
		// =========================

		if (profilePhoto != null && !profilePhoto.isEmpty()) {

			String originalFileName = profilePhoto.getOriginalFilename();

			// Create unique file name
			String fileName = System.currentTimeMillis() + "_" + originalFileName;

			String uploadPath = "C:\\JWD-69\\FinalProject\\shopping_app\\my-shoppingboot-app\\src\\main\\resources\\static\\images\\profile\\";

			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			// Save new photo
			File file = new File(uploadPath + fileName);

			profilePhoto.transferTo(file);

			// Save new image path in database
			changeProfileModel.setProfile("/images/profile/" + fileName);

		} else {

			// No new photo → keep old photo
			changeProfileModel.setProfile(oldProfile);
		}

		// =========================
		// UPDATE DATABASE
		// =========================

		int result = userService.editProfile(changeProfileModel);

		if (result > 0) {

			// =========================
			// DELETE OLD PHOTO
			// =========================

			if (profilePhoto != null && !profilePhoto.isEmpty()) {

				if (oldProfile != null && !oldProfile.isEmpty() && oldProfile.startsWith("/images/profile/")) {

					String oldFileName = oldProfile.substring("/images/profile/".length());

					String uploadPath = "C:\\JWD-69\\FinalProject\\shopping_app\\my-shoppingboot-app\\src\\main\\resources\\static\\images\\profile\\";

					File oldFile = new File(uploadPath + oldFileName);

					if (oldFile.exists()) {
						oldFile.delete();
					}
				}
			}

			// Refresh logged-in user
			UserModel updatedUser = userService.findById(loggedInUser.getId());

			session.setAttribute("loggedInUser", updatedUser);

			redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");

		} else {

			redirectAttributes.addFlashAttribute("errorMessage", "Failed to update profile.");
		}

		return "redirect:/admin/dashboard";
	}
}