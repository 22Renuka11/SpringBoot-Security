package SpringSecurity.example.SpringSecurity.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JWTFilter JWTfilter;
    @SuppressWarnings("deprecation")
    @Bean
    public AuthenticationProvider AuthProvider(){
        DaoAuthenticationProvider daoProvider=new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        daoProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable());
        http.authorizeHttpRequests(request->request.requestMatchers("/Login", "/Register").permitAll().anyRequest().authenticated());
        // http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(JWTfilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();  
    }
    @Bean
    public AuthenticationManager AuthenticationManager(AuthenticationConfiguration authConfig ) throws Exception{
        return authConfig.getAuthenticationManager();
    }
}
