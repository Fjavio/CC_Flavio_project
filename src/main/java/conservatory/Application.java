package conservatory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import conservatory.control.GestoreCorsiDiStudioConservatorio;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //Define the gestore as a Bean: spring will create it only once and "inject" it into the Controllers
    @Bean
    public GestoreCorsiDiStudioConservatorio gestore() {
        return new GestoreCorsiDiStudioConservatorio();
    }
}

