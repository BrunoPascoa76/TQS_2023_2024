package ua.tqs.bp.homework.controllers;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;

import jakarta.servlet.http.HttpSession;
import ua.tqs.bp.homework.data.User;
import ua.tqs.bp.homework.services.AuthenticationService;

@RestController
@RequestMapping("/api")
public class SessionController {

    private final AuthenticationService auth;

    @Autowired
    public SessionController(AuthenticationService auth){
        this.auth=auth;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpSession session, @RequestBody User loginDetails){
        String username=loginDetails.getUsername();
        String password=Hashing.sha256().hashString(loginDetails.getPassword(),StandardCharsets.UTF_8).toString();

        User result=auth.login(username,password);
        if(result!=null){
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            return ResponseEntity.status(200).body("Login successful");
        }else{
            return ResponseEntity.status(401).body("Username and/or password incorrect");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(HttpSession session, @RequestBody User registerDetails){
        String username=registerDetails.getUsername();
        String password=Hashing.sha256().hashString(registerDetails.getPassword(),StandardCharsets.UTF_8).toString();

        User result=auth.register(username,password);
        if(result!=null){
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            return ResponseEntity.status(200).body("Registering successful");
        }else{
            return ResponseEntity.status(409).body("Username already exists");
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        session.removeAttribute("user");
        return ResponseEntity.status(200).body("Logout successful");
    }
}
