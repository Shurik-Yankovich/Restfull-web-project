package filter;

import entity.security.User;
import entity.security.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import service.security.UserService;
import util.security.TokenHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthFilter extends GenericFilterBean {
    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private UserService userService;

//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response,
//                             Object handler){
//        String token = request.getHeader("token");
//        if (tokenHandler.checkToken(token)) {
//            User dbUser = userService.findUserById(tokenHandler.getUserIdFromToken(token));
//            UserKeeper.setLoggedUser(dbUser);
//            return true;
//        } else {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest)req);
//        UserAuthentication userAuth = Optional.ofNullable(httpRequest.getHeader(tokenHandler.AUTH_HEADER_NAME))
//                .flatMap(tokenHandler::getUserIdFromToken)
//                .flatMap(userService::findUserById)
//                .map(UserAuthentication::new)
//                .orElse(null);
        String token = httpRequest.getHeader(tokenHandler.AUTH_HEADER_NAME);
        UserAuthentication userAuth = null;
        if (tokenHandler.checkToken(token)) {
            User dbUser = userService.findUserById(tokenHandler.getUserIdFromToken(token));
            UserKeeper.setLoggedUser(dbUser);
            userAuth = new UserAuthentication(dbUser);
        }
        SecurityContextHolder.getContext().setAuthentication(userAuth);
        chain.doFilter(request, response);
    }
}
