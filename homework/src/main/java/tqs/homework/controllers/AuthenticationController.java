package tqs.homework.controllers;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;

import tqs.homework.data.User;
import tqs.homework.services.AuthenticationService;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private AuthenticationService auth;

    @Autowired
    public AuthenticationController(AuthenticationService auth){
        this.auth=auth;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginDetails){
        String username=loginDetails.getUsername();
        String password=Hashing.sha256().hashString(loginDetails.getPassword(),StandardCharsets.UTF_8).toString();


        User result=auth.login(username,password);
        if(result!=null){
            return ResponseEntity.status(200).body("{token:"+result.getToken()+"}");

        }else{
            return ResponseEntity.status(401).body("Username and/or password incorrect");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User registerDetails) throws NoSuchAlgorithmException{
        String username=registerDetails.getUsername();
        String password=Hashing.sha256().hashString(registerDetails.getPassword(),StandardCharsets.UTF_8).toString();

        User result=auth.register(username,password);
        if(result!=null){
            return ResponseEntity.status(201).body("{token:"+result.getToken()+"}");
        }else{
            return ResponseEntity.status(409).body("Username already exists");
        }
    }
}
