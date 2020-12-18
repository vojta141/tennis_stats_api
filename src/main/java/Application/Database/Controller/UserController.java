package Application.Database.Controller;

import Application.Database.DTO.UserCreateDTO;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    DataSource dataSource;

    @Secured("ROLE_ADMIN")
    @GetMapping("/isAdmin")
    public String isAdmin(){
        return "TRUE";
    }

    @Secured("ROLE_PLAYER")
    @GetMapping("/isPlayer")
    public String isPlayer(){
        return "TRUE";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/create")
    public void create(@RequestBody UserCreateDTO userCreateDTO){
        try{UserDetails newUser = User.builder()
                .username(userCreateDTO.getUsername())//password
                .password(userCreateDTO.getPassword())
                .roles("PLAYER")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(newUser);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete")
    public void delete(@RequestBody String username){
        try{
            JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
            users.deleteUser(username);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
