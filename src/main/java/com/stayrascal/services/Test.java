package com.stayrascal.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(6);
        String hashedPassword = passwordEncoder.encode("password");
        System.out.println(hashedPassword);
    }
}
