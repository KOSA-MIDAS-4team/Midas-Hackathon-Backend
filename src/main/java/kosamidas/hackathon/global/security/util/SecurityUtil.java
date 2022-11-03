package kosamidas.hackathon.global.security.util;

import kosamidas.hackathon.global.security.auth.AuthDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static AuthDetails getCurrentUser() {
        return (AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
