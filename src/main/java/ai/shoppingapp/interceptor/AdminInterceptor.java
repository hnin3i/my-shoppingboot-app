package ai.shoppingapp.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import ai.shoppingapp.model.Role;
import ai.shoppingapp.model.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception{
		
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("loggedInUser") == null) {
			
			response.sendRedirect("/login");
			return false;
			
		}
		UserDto user = (UserDto) session.getAttribute("loggedInUser");
		if (!(user.getRole().equals(Role.ADMIN))) {
			return false;
		}
		
		return true;
	}
}
