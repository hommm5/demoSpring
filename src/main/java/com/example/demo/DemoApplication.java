package com.example.demo;

import com.example.demo.user.User;
import com.example.demo.user.UserHttpClient;
import com.example.demo.user.UserRestClient;
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
    CommandLineRunner runner(UserHttpClient client) {
        return args -> {
            List<User> allUsers = client.findAll();
            System.out.println(allUsers);

            User user = client.findById(1);
            System.out.println(user);
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
