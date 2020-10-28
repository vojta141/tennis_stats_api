package Application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ApplicationTest {

    @Test
    public void check(){
        Assertions.assertEquals(1,1);
    }


}
