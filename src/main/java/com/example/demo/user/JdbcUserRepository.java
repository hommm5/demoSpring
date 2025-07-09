package com.example.demo.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class JdbcUserRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcUserRepository.class);
    private final JdbcClient client;

    public JdbcUserRepository(JdbcClient client) {
        this.client = client;
    }

    public void insertGeo(User user){
        int update = client.sql("INSERT INTO Geo (lat, lng) VALUES (?, ?)")
                .params(user.address().geo().lat(), user.address().geo().lng())
                .update();

        Assert.state(update == 1, "Failed to insert Geo");
        log.info("Geo was inserted.");
    }

    public void insertCompany(User user){
        int update = client.sql("INSERT INTO Company (name, catchPhrase, bs) VALUES (?, ?, ?)")
                .params(user.company().name(), user.company().catchPhrase(), user.company().bs())
                .update();

        Assert.state(update == 1, "Failed to insert Geo");
        log.info("Company was inserted.");
    }
}
