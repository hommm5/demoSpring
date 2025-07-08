package com.example.demo.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.*;

@RestClientTest
class UserRestClientTest {

    @Autowired
    MockRestServiceServer server;

    @Autowired
    UserRestClient client;

    @Autowired
    ObjectMapper mapper;

    @Test
    void shouldFindAllUser(){
        //Given

        UserX userX1 = new UserX(1,
                "Leanne",
                "lgraham",
                "lgraham@gmail.com",
                new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",
                        new Geo("-37.3159", "81.1496")),
                "1-770-736-8031 x56442",
                "hildegard.org",
                new Company("Romaguera-Crona",
                        "Multi-layered cliend-server neural-net",
                        "harness real-time e-markets"));
    }


}