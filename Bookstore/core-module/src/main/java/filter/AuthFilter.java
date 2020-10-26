package filter;

import entity.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import service.security.UserService;
import util.security.TokenHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends HandlerInterceptorAdapter {
    @Autowired
    TokenHandler tokenHandler;
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler){
        String token = request.getHeader("token");
        if (tokenHandler.checkToken(token)) {
            User dbUser = userService.findUserById(tokenHandler.getUserIdFromToken(token));
            UserKeeper.setLoggedUser(dbUser);
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
