package Application.Database.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping("/authorization")
public class UserController {

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

}
