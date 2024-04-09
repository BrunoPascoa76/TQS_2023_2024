package tqs.homework.repositories;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.homework.data.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Cacheable("users")
    public Optional<User> findByUsername(String username);
    @Cacheable("users")
    public Optional<User> findByUsernameAndPassword(String username, String password);
    @Cacheable("users")
    public Optional<User> findByToken(String token);
}
