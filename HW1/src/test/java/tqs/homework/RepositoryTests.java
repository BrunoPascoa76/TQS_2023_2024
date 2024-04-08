package tqs.homework;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tqs.homework.data.*;
import tqs.homework.repositories.*;

@DataJpaTest
class RepositoryTests {

    private UserRepository userRepo;

    @Autowired
    public RepositoryTests(UserRepository userRepo){
        this.userRepo=userRepo;
    }
    
    @Test
    void testUserInsertionAndFindByUsername(){
        User joao=new User("João","password");
        joao=userRepo.save(joao);
        assertEquals(joao, userRepo.findByUsername("João").get());
    }

    @Test
    void testUserFindByUsernameAndPassword(){
        User joao=new User("João","password");
        joao=userRepo.save(joao);
        assertEquals(joao, userRepo.findByUsernameAndPassword("João","password").get());
    }

    @Test
    void testUserFindByToken(){
        User joao=new User("João","password");
        joao.setToken("abc");
        joao=userRepo.save(joao);
        assertEquals(joao, userRepo.findByToken("abc").get());
    }

    //More tests could be added, but they aren't that important (since we already estabilished JPA is working well)
}
