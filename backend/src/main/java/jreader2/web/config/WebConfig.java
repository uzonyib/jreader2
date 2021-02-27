package jreader2.web.config;

import jreader2.service.UserService;
import jreader2.web.interceptor.AppengineInterceptor;
import jreader2.web.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringAntMatchers("/tasks/**", "/rest/**"))
                .authorizeRequests(authorize -> authorize
                        .mvcMatchers("/cron/**").permitAll()
                        .mvcMatchers("/tasks/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login();
        return http.build();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(userService)).addPathPatterns("/", "/reader/**", "/rest/**");
        registry.addInterceptor(new AppengineInterceptor("X-Appengine-Cron")).addPathPatterns("/cron/**");
        registry.addInterceptor(new AppengineInterceptor("X-AppEngine-QueueName")).addPathPatterns("/tasks/**");
    }

}
