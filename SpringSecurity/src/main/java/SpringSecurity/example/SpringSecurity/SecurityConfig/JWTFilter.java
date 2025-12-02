package SpringSecurity.example.SpringSecurity.SecurityConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import SpringSecurity.example.SpringSecurity.SecurityService.JWTService;
import SpringSecurity.example.SpringSecurity.SecurityService.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JWTFilter extends OncePerRequestFilter {
@Autowired
JWTService jwtservice;
@Autowired
ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String AuthHeader=request.getHeader("Authorization");
        String username=null;
        String token=null;
        if(AuthHeader!=null && AuthHeader.startsWith("Bearer ")){
            token=AuthHeader.substring(7);
            username=jwtservice.getUserName(token);
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=context.getBean(SecurityService.class).loadUserByUsername(username);
            if(jwtservice.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
