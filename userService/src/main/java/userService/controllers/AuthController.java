package userService.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Return user details
            return ResponseEntity.ok(authentication.getPrincipal());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/session-info")
    public ResponseEntity<?> getSessionInfo(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(authentication.getPrincipal());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"authenticated\": false}");
    }

    @PostMapping("/check-session")
    public ResponseEntity<?> checkSession(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok().body("{\"authenticated\": true}");
        }
        return ResponseEntity.ok().body("{\"authenticated\": false}");
    }

    // Add this endpoint to manually check session
    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            return ResponseEntity.ok().body("{\"authenticated\": true}");
        }
        return ResponseEntity.ok().body("{\"authenticated\": false}");
    }
}