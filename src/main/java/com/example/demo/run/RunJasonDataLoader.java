package com.example.demo.run;

import com.example.demo.DemoApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
@Component
public class RunJasonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
    private final JdbcClientRunRepository repository;
    private final ObjectMapper objectMapper;
    public RunJasonDataLoader(JdbcClientRunRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0){
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")){
            Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
            log.info("Reading all {} JSON runs files and saving them in database.", allRuns.runs().size());
            repository.saveAll(allRuns.runs());
            }catch (IOException e){
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading runs from JSON, because the collection contains data");
        }
    }
}
