package *;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Example {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
        UserInput input = new UserInput();
            input.name();
    }
}
 class UserInput {
     
      public String name() {
      
        return "Hello World - v3!";
      
    }
 }
