package com.stayrascal.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(6);
        String hashedPassword = passwordEncoder.encode("password");
        System.out.println(passwordEncoder.matches("password", "$2a$06$dl1J.PeDVnliIU6bFGR8kuSShAVaylnaieZ872Em2be7ADUcTeUEG"));
        System.out.println(passwordEncoder.matches("password", "$2a$06$FxYloy7rsuuInOsthcjT5uthklfuVqUvOQoc172JXGJK3uEQnDl82"));
        System.out.println(hashedPassword);
        System.out.println(passwordEncoder.encode("password"));
        System.out.println(passwordEncoder.encode("password"));
        System.out.println(passwordEncoder.encode("password"));
    }
}
