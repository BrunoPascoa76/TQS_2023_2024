package tqs.homework.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.homework.data.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    public Optional<User> findByUsername(String username);
    public Optional<User> findByUsernameAndPwd(String username, String pwd);
    public Optional<User> findByTkn(String tkn);
}
