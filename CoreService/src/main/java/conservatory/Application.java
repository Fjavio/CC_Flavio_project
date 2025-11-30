package conservatory;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import conservatory.control.GestoreCorsiDiStudioConservatorio;
import conservatory.database.CorsoDAO;
import conservatory.database.DocenteDAO;
import conservatory.database.EsameDAO;
import conservatory.database.StudenteDAO;
import conservatory.database.VerbaleDAO;
import conservatory.control.ExternalService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //DAO BEAN DEFINITION ---
    //SpringBoot configures the DataSource (MySQL or H2) and automatically injects it as a parameter into these methods

    @Bean
    public CorsoDAO courseDAO(DataSource dataSource) {
        return new CorsoDAO(dataSource);
    }
    
    @Bean
    public DocenteDAO teacherDAO(DataSource dataSource) {
        return new DocenteDAO(dataSource);
    }

    @Bean
    public StudenteDAO studentDAO(DataSource dataSource) {
        return new StudenteDAO(dataSource);
    }

    @Bean
    public VerbaleDAO reportDAO(DataSource dataSource) {
        return new VerbaleDAO(dataSource);
    }

    @Bean
    public EsameDAO examDAO(DataSource dataSource, StudenteDAO studentDAO, CorsoDAO courseDAO, VerbaleDAO reportDAO) {
        return new EsameDAO(dataSource, studentDAO, courseDAO, reportDAO);
    }  
    
    @Bean
    public GestoreCorsiDiStudioConservatorio gestore(
            CorsoDAO courseDAO, 
            DocenteDAO teacherDAO,
            EsameDAO examDAO, 
            StudenteDAO studentDAO,
            VerbaleDAO reportDAO,
            ExternalService externalService //NEW
    ) {
        return new GestoreCorsiDiStudioConservatorio(courseDAO, teacherDAO, examDAO, studentDAO, reportDAO, externalService);
    }
}
