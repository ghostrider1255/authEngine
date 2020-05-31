package com.javasree.ms.authengine.security;

import com.javasree.ms.authengine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{

    @Autowired private UserService userService;
    @Autowired private Environment environment;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserService userService,Environment environment,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.environment = environment;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**")
                .permitAll()
                //.hasIpAddress(this.environment.getProperty("my.gateway.ip"))
                //.access("hasIpAddress('"+this.environment.getProperty("my.gateway.ip")+"')")
                //.hasAnyRole("READ","WRITE")

                //http.authorizeRequests.antMatchers("/**").hasIpAddress(environment.getProperty("my.gatekeeper.ip"))
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    private AuthFilter getAuthenticationFilter() throws Exception{
        AuthFilter authFilter = new AuthFilter(userService,environment,this.authenticationManager());
        //authFilter.setAuthenticationManager(authenticationManager());
        authFilter.setFilterProcessesUrl(environment.getProperty("api.login.url.path"));
        return authFilter;
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth)
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
