package com.codecentric.retailbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class RetailbankApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(RetailbankApplication.class, args);
    }

}

