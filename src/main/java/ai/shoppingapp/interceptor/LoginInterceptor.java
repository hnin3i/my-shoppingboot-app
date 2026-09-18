package ai.shoppingapp.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
	
@Component
public class LoginInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("loggedInUser") == null) {
	        // MUST explicitly redirect if returning false!
	        response.sendRedirect(request.getContextPath() + "/errors/not-authorized");
	        return false;
	    }
	    return true;
	}
}
