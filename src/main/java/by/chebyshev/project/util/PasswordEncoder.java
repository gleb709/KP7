package by.chebyshev.project.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public org.springframework.security.crypto.password.PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
}
