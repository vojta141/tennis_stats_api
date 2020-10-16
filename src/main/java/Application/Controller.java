package Application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("/request")
    public void requestedValue()
    {
        System.out.println("Request received");
    }


}
