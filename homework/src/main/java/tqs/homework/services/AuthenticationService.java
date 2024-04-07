package tqs.homework.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.homework.data.User;
import tqs.homework.repositories.UserRepository;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository repo;

    public User login(String username, String password){
        Optional<User> result=repo.findByUsernameAndPassword(username, password);
        if(result.isPresent()){
            return result.get();
        }else{
            return null;
        }
    }

    public User register(String username, String password){
        Optional<User> result=repo.findByUsername(username);
        if(!result.isPresent()){
            User user=new User(username,password);
            repo.save(user);
            return user;
        }else{
            return null;
        }
    }
}
