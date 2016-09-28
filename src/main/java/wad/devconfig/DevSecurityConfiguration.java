
package wad.devconfig;

import wad.configuration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import wad.service.JpaAuthenticationProvider;

@EnableWebSecurity
@Configuration
@Profile("development")
public class DevSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private JpaAuthenticationProvider authProvider;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("devaa");
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        
        http.authorizeRequests()
                .antMatchers("/css/**", "/signup").permitAll();
        http.authorizeRequests()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login").permitAll().and()
                .logout().permitAll();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
}
