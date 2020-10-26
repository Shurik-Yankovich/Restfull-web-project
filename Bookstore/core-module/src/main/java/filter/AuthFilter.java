package filter;

import entity.security.User;
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
    TokenHandler tokenHandler;
    @Autowired
    UserService userService;

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
        Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest)req);
        Optional.ofNullable(request.getHeader(AUTH_HEADER_NAME))
                .flatMap(tokenHandler::exstractUserId)
                .flatMap(userService::findById)
                .map(UserAuth::new);
        SecurityContextHolder.getContext().setAuthentication(tokenService.getAuthentication(request).orElse(null));
        chain.doFilter(request, response);
    }
}
