package SpringSecurity.example.SpringSecurity.SecurityService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import SpringSecurity.example.SpringSecurity.Model.UserLogin;
import SpringSecurity.example.SpringSecurity.Model.UserLoginDetails;
import SpringSecurity.example.SpringSecurity.SecurityRepository.SecurityRepository;

@Service
public class SecurityService implements UserDetailsService{
    @Autowired
    SecurityRepository securityRepository;
    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public List<UserLogin> UserDetails() {
        List<UserLogin> UserLoginDetails = 
					new ArrayList<>(List.of(
                        new UserLogin("Renuka", "Renu@2211"),
                        new UserLogin("Atharva", "Atharva@2307"))
							);

        securityRepository.saveAll(UserLoginDetails);
        return UserLoginDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserLogin userName=securityRepository.findByUserName(username);
    if(userName ==null){
        throw new UnsupportedOperationException("Not supported yet.");
    }
        return new UserLoginDetails(userName);
    }

    public void RegisterData(UserLogin register) {
        register.setPassword(passwordEncoder.encode(register.getPassword()));
        securityRepository.save(register);
    }

    public List<UserLogin> UserLoginDetailsList() {
        return securityRepository.findAll();
    }
    
}
