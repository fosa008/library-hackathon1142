package com.example.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(LibraryApplication.class, args);
        } catch (Exception e) {
            System.err.println("!!! SPRING BOOT CRASHED DURING STARTUP !!!");
            e.printStackTrace();
        }
    }
}