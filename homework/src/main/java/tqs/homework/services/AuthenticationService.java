package tqs.homework.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

import tqs.homework.data.User;
import tqs.homework.repositories.UserRepository;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository repo;

    public User login(String username, String password){
        Optional<User> result=repo.findByUsernameAndPwd(username, password);
        if(result.isPresent()){
            return result.get();
        }else{
            return null;
        }
    }

    public User register(String username, String password) throws NoSuchAlgorithmException{
        Optional<User> result=repo.findByUsername(username);
        if(!result.isPresent()){
            User user=new User(username,password);
            user.setToken(generateToken());
            repo.save(user);
            return user;
        }else{
            return null;
        }
    }

    public static String generateToken() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] bytes = new byte[32]; // Adjust length as needed (longer is stronger)
        random.nextBytes(bytes);
        return Hashing.sha256().hashBytes(bytes).toString();
    }
}
