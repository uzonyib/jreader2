package jreader2.app.web.interceptor;

import jreader2.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        OAuth2AuthenticationToken auth =
                (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().getAttribute("email");
        if (!userService.isAuthorized(email)) {
            response.getWriter().write("Forbidden");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        return true;
    }

}
