package ai.shoppingapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 400 - BAD REQUEST
    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(Exception ex, Model model) {
        populateErrorModel(model, 400, "Bad Request", ex.getMessage(), false);
        return "error/error";
    }

    // 401 - UNAUTHORIZED (Must log in)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(UnauthorizedException ex, Model model) {
        populateErrorModel(model, 401, "Login Required", ex.getMessage(), true);
        return "error/error";
    }

    // 403 - FORBIDDEN (Admin / Role mismatch)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        populateErrorModel(model, 403, "Access Denied", ex.getMessage(), true);
        return "error/error";
    }

    // 404 - RESOURCE NOT FOUND
    @ExceptionHandler({ResourceNotFoundException.class, NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(Exception ex, Model model) {
        populateErrorModel(model, 404, "Not Found", 
            ex instanceof ResourceNotFoundException ? ex.getMessage() : "The requested page or item does not exist.", 
            false);
        return "error/error";
    }

    // 409 - CONFLICT (Duplicate email, stock clash)
    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConflict(DuplicateResourceException ex, Model model) {
        populateErrorModel(model, 409, "Conflict Detected", ex.getMessage(), false);
        return "error/error";
    }

    // 413 - PAYLOAD TOO LARGE (Image uploads)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public String handleMaxUpload(MaxUploadSizeExceededException ex, Model model) {
        populateErrorModel(model, 413, "File Too Large", "Uploaded image exceeds the maximum permitted file size.", false);
        return "error/error";
    }

    // 500 - DATABASE ERRORS
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDatabaseError(DataAccessException ex, Model model) {
        log.error("Database error occurred: ", ex);
        populateErrorModel(model, 500, "Database Error", "A database query or persistence operation failed. Please try again.", false);
        return "error/error";
    }

    // 500 - GENERAL INTERNAL SERVER ERROR
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        log.error("Unhandled exception occurred: ", ex);
        populateErrorModel(model, 500, "Server Error", "An unexpected system error occurred. Our team has been notified.", false);
        return "error/error";
    }
 // 404 - No record found in Database
    @ExceptionHandler(org.springframework.dao.EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmptyResult(EmptyResultDataAccessException ex, Model model) {
        populateErrorModel(model, 404, "Not Found", "The requested record does not exist.", false);
        return "error/error";
    }
    

    private void populateErrorModel(Model model, int status, String title, String message, boolean showLoginBtn) {
        model.addAttribute("status", status);
        model.addAttribute("title", title);
        model.addAttribute("message", (message != null && !message.isBlank()) ? message : "An unexpected event occurred.");
        model.addAttribute("showLoginBtn", showLoginBtn);
    }
}