package lorekeeper.com.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lorekeeper.com.filter.ExceptionHandlerFilter;
import lorekeeper.com.filter.JWTAuthenticationFilter;
import lorekeeper.com.filter.LoginAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;
    @Qualifier("restExceptionHandler")
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        http.csrf().disable()
            .authorizeHttpRequests((authorize) -> {
                authorize.requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
                         .anyRequest().authenticated();
            })
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(exceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(jwtAuthenticationFilter(), LoginAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {

        return new ExceptionHandlerFilter(handlerExceptionResolver);

    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() {

        return new LoginAuthenticationFilter(authenticationManager());

    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {

        return new JWTAuthenticationFilter(authenticationManager());

    }

    @Bean
    public AuthenticationManager authenticationManager() {

        return new ProviderManager(authenticationProvider());

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

}
