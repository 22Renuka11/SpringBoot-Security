package SpringSecurity.example.SpringSecurity.Model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity   // ✅ Must include this
public class UserLogin {
    @Id
    String userName;
    String Password;
}
