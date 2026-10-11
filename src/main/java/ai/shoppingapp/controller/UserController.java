
package ai.shoppingapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ai.shoppingapp.model.OrderHistoryDto;
import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.model.usermanagement.ChangeProfileModel;
import ai.shoppingapp.service.OrderHistoryService;
import ai.shoppingapp.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final OrderHistoryService orderHistoryService;

	public UserController(UserService userService, OrderHistoryService orderHistoryService) {

		this.userService = userService;
		this.orderHistoryService = orderHistoryService;
	}

	// =========================================================
	// CHECK LOGIN AND ROLE
	// =========================================================

	private String checkCustomerAccess(HttpSession session) {

		UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}
		
		if ("ADMIN".equalsIgnoreCase(String.valueOf(loggedInUser.getRole())) || "SUPER_ADMIN".equalsIgnoreCase(String.valueOf(loggedInUser.getRole()))) {

			return "redirect:/admin/dashboard";
		}

		return null;
	}

	// =========================================================
	// USER PROFILE CHANGES PAGE
	// URL: GET /user/profilechanges
	// =========================================================

	@GetMapping("/profilechanges")
	public String showEditProfilePage(HttpSession session, Model model) {

		String accessResult = checkCustomerAccess(session);

		if (accessResult != null) {
			return accessResult;
		}

		UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");

		UserModel currentUser = userService.findById(loggedInUser.getId());

		if (currentUser == null) {
			return "redirect:/login";
		}

		ChangeProfileModel changeProfileModel = new ChangeProfileModel();

		changeProfileModel.setId(currentUser.getId());
		changeProfileModel.setName(currentUser.getName());
		changeProfileModel.setEmail(currentUser.getEmail());
		changeProfileModel.setPhone(currentUser.getPhone());
		changeProfileModel.setAddress(currentUser.getAddress());
		changeProfileModel.setProfile(currentUser.getProfile());

		model.addAttribute("changeProfileModel", changeProfileModel);

		return "user/profilechanges";
	}

	// =========================================================
	// SAVE PROFILE CHANGES
	// URL: POST /user/profilechanges
	// =========================================================

	@PostMapping("/profilechanges")
	public String processEditProfile(@ModelAttribute("changeProfileModel") ChangeProfileModel changeProfileModel,
			BindingResult result, @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
			HttpSession session, Model model, RedirectAttributes redirectAttributes) throws IOException {

		String accessResult = checkCustomerAccess(session);

		if (accessResult != null) {
			return accessResult;
		}

		UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");

		UserModel currentUser = userService.findById(loggedInUser.getId());

		if (currentUser == null) {
			return "redirect:/login";
		}

		// Always use the logged-in user's ID
		changeProfileModel.setId(loggedInUser.getId());

		// CHECK DUPLICATE EMAIL
		if (userService.isEmailAlreadyUsed(changeProfileModel.getEmail(), changeProfileModel.getId())) {

			model.addAttribute("duplicateEmail", true);

			return "user/profilechanges";
		}

		// KEEP OLD PROFILE IMAGE
		String oldProfile = currentUser.getProfile();

		// PROFILE PHOTO UPLOAD
		if (profilePhoto != null && !profilePhoto.isEmpty()) {

			String originalFileName = profilePhoto.getOriginalFilename();

			if (originalFileName != null && !originalFileName.isBlank()) {

				// Remove any directory information from filename
				String safeFileName = new File(originalFileName).getName();

				String fileName = System.currentTimeMillis() + "_" + safeFileName;

				String uploadPath = "C:\\JWD-69\\FinalProject\\shopping_app\\my-shoppingboot-app\\src\\main\\resources\\static\\images\\profile\\";

				File uploadDir = new File(uploadPath);

				if (!uploadDir.exists() && !uploadDir.mkdirs()) {

					throw new IOException("Could not create profile upload directory.");
				}

				File newFile = new File(uploadDir, fileName);

				profilePhoto.transferTo(newFile);

				changeProfileModel.setProfile("/images/profile/" + fileName);

			} else {
				changeProfileModel.setProfile(oldProfile);
			}

		} else {
			changeProfileModel.setProfile(oldProfile);
		}

		// UPDATE DATABASE
		int updateResult = userService.editProfile(changeProfileModel);

		if (updateResult > 0) {

			// DELETE OLD PHOTO AFTER SUCCESSFUL UPDATE
			if (profilePhoto != null && !profilePhoto.isEmpty() && oldProfile != null
					&& oldProfile.startsWith("/images/profile/")) {

				String oldFileName = oldProfile.substring("/images/profile/".length());

				String uploadPath = "C:\\JWD-69\\FinalProject\\shopping_app\\my-shoppingboot-app\\src\\main\\resources\\static\\images\\profile\\";

				File oldFile = new File(uploadPath, oldFileName);

				if (oldFile.exists()) {
					oldFile.delete();
				}
			}

			// REFRESH SESSION WITH UPDATED USER DATA
			UserModel updatedUser = userService.findById(loggedInUser.getId());

			if (updatedUser != null) {
				session.setAttribute("loggedInUser", updatedUser);
			}

			redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");

		} else {

			redirectAttributes.addFlashAttribute("errorMessage", "Failed to update profile. Please try again.");
		}

		return "redirect:/user/profile";
	}

	// =========================================================
	// USER PROFILE VIEW
	// URL: GET /user/profile
	// =========================================================

	@GetMapping("/profile")
	public String profile(HttpSession session, Model model) {

		String accessResult = checkCustomerAccess(session);

		if (accessResult != null) {
			return accessResult;
		}

		UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");

		UserModel user = userService.findById(loggedInUser.getId());

		if (user == null) {
			return "redirect:/login";
		}

		model.addAttribute("user", user);

		return "user/profile";
	}

	// =========================================================
	// USER DASHBOARD
	// URL: GET /user/dashboard
	// =========================================================

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {

		String accessResult = checkCustomerAccess(session);

		if (accessResult != null) {
			return accessResult;
		}

		UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");

		model.addAttribute("loggedInUser", loggedInUser);

		String userId = loggedInUser.getId();

		List<OrderHistoryDto> latestOrders = orderHistoryService.getLatestOrders(userId);

		model.addAttribute("latestOrders", latestOrders);

		return "user/dashboard";
	}
}
