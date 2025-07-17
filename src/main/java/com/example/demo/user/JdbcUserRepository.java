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

    public Integer insertGeo(User user) {
        String returnedObject = client.sql("INSERT INTO Geo (lat, lng) VALUES (?, ?) RETURNING id")
                .params(user.address().geo().lat(), user.address().geo().lng())
                .query()
                .singleValue().toString();


        log.info("Geo was inserted.");
        return Integer.parseInt(returnedObject);

    }

    public Integer insertCompany(User user) {
        String returnedObject = client.sql("INSERT INTO Company (name, catchPhrase, bs) VALUES (?, ?, ?) RETURNING id")
                .params(user.company().name(), user.company().catchPhrase(), user.company().bs())
                .query()
                .singleValue().toString();

        log.info("Company was inserted.");
        return Integer.parseInt(returnedObject);
    }

    public Integer insertAddress(User user, Integer id) {
        String returnedObject = client.sql("INSERT INTO Address (street, suite, city, zipcode, geo_id) VALUES (?, ?, ?, ?, ?) RETURNING id")
                .params(user.address().street(), user.address().suite(), user.address().city(), user.address().zipcode(), id)
                .query()
                .singleValue().toString();

        log.info("Address was inserted.");

        return Integer.parseInt(returnedObject);
    }

    public void insertUser(User user, Integer addressId, Integer companyId){
        int updated = client.sql("INSERT INTO \"user\" (name, username, email, address_id, company_id) VALUES (?, ?, ?, ?, ?)")
                .params(user.name(), user.username(), user.email(), addressId, companyId)
                .update();

        Assert.state(updated == 1, "Failed to create User " + user.name());
    }

    public List<User> getAllUsers(){
        return client.sql("SELECT u.id AS user_id, u.name AS user_name,u.username AS user_username, u.email AS user_email" +
                        "a.street, a.suite, a.city, a.zipcode," +
                        "g.lat, g.lng," +
                        "c.name AS company_name, c.catchPhrase, c.bs FROM \"user\" u " +
                        "JOIN address a ON u.addres_id = a.id " +
                        "JOIN geo g ON a.geo_id = g.id " +
                        "JOIN company c ON u.company_id = c.id")
                .query((rs, rowNum) -> {
                    Geo geo = new Geo(
                            rs.getString("lat"),
                            rs.getString("lng")
                    );
                    Address address = new Address(
                            rs.getString("street"),
                            rs.getString("suite"),
                            rs.getString("city"),
                            rs.getString("zipcode"),
                            geo
                    );

                    Company company = new Company(
                            rs.getString("company_name"),
                            rs.getString("catchPhrase"),
                            rs.getString("bs")
                    );

                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("user_name"),
                            rs.getString("username"),
                            rs.getString("email"),
                            address,
                            company
                    );
                })
                .list();

    }

}
