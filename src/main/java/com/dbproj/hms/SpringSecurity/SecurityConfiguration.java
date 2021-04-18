package com.dbproj.hms.SpringSecurity;

import com.dbproj.hms.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)

                .usersByUsernameQuery("select username,password,verify "+
                        "from employee "
                + "where username=?")
                .authoritiesByUsernameQuery("select username,authorization "
                +"from employee "
                + "where username=?");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN","USER")
                .antMatchers("/").authenticated()


                .antMatchers("/doc/{docid}").hasAnyRole("ADMIN","USER")
                .antMatchers("/doc/{docid}/update").hasRole("ADMIN")
                .antMatchers("/doc/{docid}/delete").hasRole("ADMIN")
                .antMatchers("/nur/{nurseid}/delete").hasRole("ADMIN")
                .antMatchers("/nur/{nurseid}/delete").hasRole("ADMIN")
                .antMatchers("/nmp/{nmpID}/delete").hasRole("ADMIN")
                .antMatchers("/nmp/{nmpID}/delete").hasRole("ADMIN")
                .antMatchers("/doc/{docid}/timings").hasAnyRole("ADMIN","USER")
                .antMatchers("/nur/{nurseid}").hasAnyRole("ADMIN","USER")
                .antMatchers("/nur/{nurseid}/addnurse").hasAnyRole("ADMIN")
                .antMatchers("/transaction").hasAnyRole("ADMIN")
                .antMatchers("/adddoctor").hasAnyRole("ADMIN")
                .antMatchers("/nmp/new").hasAnyRole("ADMIN")
                .antMatchers("/patient/new").hasAnyRole("ADMIN")
                .antMatchers("/doc/all").hasAnyRole("ADMIN","USER")
                .antMatchers("/nmp/all").hasAnyRole("ADMIN","USER")
                .antMatchers("/nur/all").hasAnyRole("ADMIN","USER")
                .antMatchers("/patient/new").hasAnyRole("ADMIN")
                .antMatchers("/patient/update").hasAnyRole("ADMIN")
                .antMatchers("/doc/{docid}/bookAppointment").hasAnyRole("ADMIN")
                .and().formLogin()
                .defaultSuccessUrl("/")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                 .and()
                .logout().permitAll();

        http.cors().and().csrf().disable();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        /* BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;*/
        return NoOpPasswordEncoder.getInstance();
    }
}

