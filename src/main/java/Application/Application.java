package Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;
import java.sql.Connection;
@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class Application {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private void configureJdbcAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Add admin account
     */
    /*@Bean
    UserDetailsManager users(DataSource dataSource) {
        String adminPassword = "password";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}" + encoder.encode(adminPassword))
                .roles("ADMIN")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(admin);
        return users;
    }*/
}
