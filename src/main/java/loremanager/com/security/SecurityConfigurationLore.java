package loremanager.com.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableWebSecurity @AllArgsConstructor
public class SecurityConfigurationLore extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login", "/token/refresh", "/test").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/user").permitAll();;
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/user/**").permitAll();
        //http.authorizeRequests().antMatchers(HttpMethod.GET, "/user/**").hasAuthority(Role.USER.name());
        http.authorizeRequests().anyRequest().authenticated();

        AuthenticationFilterLore authenticationFilterLore = new AuthenticationFilterLore(authenticationManagerBean());
        autowireCapableBeanFactory.autowireBean(authenticationFilterLore);
        http.addFilter(authenticationFilterLore);
        http.addFilterBefore(new AuthorizationFilterLore(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();

    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
