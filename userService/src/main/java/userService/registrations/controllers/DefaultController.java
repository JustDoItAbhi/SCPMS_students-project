package userService.registrations.controllers;



import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DefaultController implements ErrorController {

//    @RequestMapping("/")
//    public String home() {
//        return "home";
//    }
@GetMapping("/.well-known/appspecific/com.chrome.devtools.json")
public Map<String, Object> chromeCheck() {
    System.out.println("error from well known ");
    return Map.of("status", "ok");
}

//    @RequestMapping("/login")
//    public String login() {
//        return "OAuth2 Server is running! Go to: http://localhost:8080/oauth2/authorize?response_type=code&client_id=abhi&redirect_uri=http://localhost:5173/callback&scope=profile";
//    }

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            errorDetails.put("status", statusCode);
            errorDetails.put("error", HttpStatus.valueOf(statusCode).getReasonPhrase());
        } else {
            errorDetails.put("status", 404);
            errorDetails.put("error", "Not Found");
        }

        errorDetails.put("message", message != null ? message : "No message available");
        errorDetails.put("path", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

        if (exception != null) {
            errorDetails.put("exception", exception.getClass().getName());
        }

        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf((Integer) errorDetails.get("status")));
    }
}