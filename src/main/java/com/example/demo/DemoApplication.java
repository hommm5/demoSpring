package com.example.demo;

import com.example.demo.user.JdbcUserRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;


@SpringBootApplication
public class DemoApplication {


    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

    @Bean
    UserHttpClient userHttpClient(){
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(UserHttpClient.class);
    }

    @Bean
    CommandLineRunner runner(UserHttpClient client, JdbcUserRepository repo) {
        return args -> {
            List<User> allUsersFromHttpClient = client.findAll();

            System.out.println(allUsersFromHttpClient);

            for (User currentUser : allUsersFromHttpClient) {
                Integer geoId = repo.insertGeo(currentUser);
                log.info("The id is {}", geoId);
                Integer companyId = repo.insertCompany(currentUser);
                log.info("The id is {}", companyId);
                Integer addressId = repo.insertAddress(currentUser, geoId);
                repo.insertUser(currentUser, addressId, companyId);
            }

            List<User> usersFromDB = client.findAll();
            System.out.println(usersFromDB);


        };
    }

    /*@Bean
    CommandLineRunner runner(RunRepository repository){
        return args ->{
            Run run = new Run(1,
                    "First Run",
                    LocalDateTime.now(),
                    LocalDateTime.now().plus(1, ChronoUnit.HOURS),
                    5,
                    Location.OUTDOOR);

            log.info("Run: " + run);

            repository.create(run);

        };

    }*/
}
