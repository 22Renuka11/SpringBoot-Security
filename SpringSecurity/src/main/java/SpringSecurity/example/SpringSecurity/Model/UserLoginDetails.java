package SpringSecurity.example.SpringSecurity.Model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserLoginDetails implements UserDetails {
    UserLogin UserLogin;
    public UserLoginDetails(UserLogin userLogin){
        this.UserLogin=userLogin;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return UserLogin.getPassword();
    }

    @Override
    public String getUsername() {
        return UserLogin.getUserName();
    }
    
}
