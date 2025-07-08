package com.example.demo.user;

public record UserX(Integer id,
                    String name,
                    String username,
                    String email,
                    Address address,
                    String phone,
                    String website,
                    Company company) {
}
