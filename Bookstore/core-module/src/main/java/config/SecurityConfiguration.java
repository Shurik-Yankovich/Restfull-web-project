package config;

import filter.AuthFilter;
import filter.CustomLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import service.security.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthFilter authFilter;
    @Autowired
    private CustomLogoutHandler logoutHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                //Доступ только для не зарегистрированных пользователей
////                .antMatchers("/registration").not().fullyAuthenticated()
//                //Доступ только для пользователей с ролью Администратор
//                .antMatchers("/requests/**", "/orders/**").hasRole("ADMIN")
////                .antMatchers("/orders/**").hasRole("USER")
//                //Доступ разрешен всем пользователей
//                .antMatchers("/", "/registration/", "/storage/**").permitAll()
//                //Все остальные страницы требуют аутентификации
//                .anyRequest().authenticated()
//                .and()
//                //Настройка для входа в систему
//                .formLogin()
//                .loginPage("/login")
//                //Перенарпавление на главную страницу после успешного входа
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                .addFilterBefore(new AuthFilter(), UsernamePasswordAuthenticationFilter.class)
//                .logout()
//                .permitAll()
//                .logoutSuccessUrl("/");

//        httpSecurity.httpBasic()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/requests/**", "/orders/**").hasRole("ADMIN")
//                .antMatchers("/", "/registration/", "/login/", "/storage/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .logout().permitAll();
//        httpSecurity.headers().disable();

        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/requests/**").hasRole("ADMIN")
//                .antMatchers("/orders/**").hasRole("USER")
                .antMatchers("/", "/registration/", "/login/", "/storage/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .permitAll();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
