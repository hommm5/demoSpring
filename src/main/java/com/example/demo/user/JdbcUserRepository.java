package com.example.demo.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class JdbcUserRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcUserRepository.class);
    private final JdbcClient client;

    public JdbcUserRepository(JdbcClient client) {
        this.client = client;
    }

    public Integer insertGeo(User user) {
        String returnedObject = client.sql("INSERT INTO Geo (lat, lng) VALUES (?, ?) RETURNING id")
                .params(user.address().geo().lat(), user.address().geo().lng())
                .query()
                .singleValue().toString();


        log.info("Geo was inserted.");

        return Integer.parseInt(returnedObject);

    }

    public void insertCompany(User user) {
        int update = client.sql("INSERT INTO Company (name, catchPhrase, bs) VALUES (?, ?, ?)")
                .params(user.company().name(), user.company().catchPhrase(), user.company().bs())
                .update();

        Assert.state(update == 1, "Failed to insert Company");
        log.info("Company was inserted.");
    }

    public void insertAddress(User user) {
        int update = client.sql("INSERT INTO Address (street, suite, city, zipcode) VALUES (?, ?, ?, ?)")
                .params(user.address().street(), user.address().suite(), user.address().city(), user.address().zipcode())
                .update();

        Assert.state(update == 1, "Failed to insert Address");
        log.info("Address was inserted.");
    }
}
