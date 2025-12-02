package SpringSecurity.example.SpringSecurity.SecurityRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SpringSecurity.example.SpringSecurity.Model.UserLogin;

@Repository
public interface  SecurityRepository extends JpaRepository<UserLogin, String>{
    UserLogin findByUserName(String userName);
}
