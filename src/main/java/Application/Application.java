package Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
@SpringBootApplication

public class Application {

    private static Connection connection;

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}
