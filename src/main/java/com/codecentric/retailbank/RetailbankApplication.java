package com.codecentric.retailbank;

import com.codecentric.retailbank.configuration.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class RetailbankApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetailbankApplication.class, args);
    }

}

