package com.example.demo.user;


public record User(
        Integer id,
        String name,
        String username,
        String email,
        Address address,
        Company company) {
}
