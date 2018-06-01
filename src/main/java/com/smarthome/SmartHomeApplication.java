package com.smarthome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by wushenjun on 2018/5/31.
 */
@SpringBootApplication
public class SmartHomeApplication {
    private static final Logger logger = LoggerFactory.getLogger(SmartHomeApplication.class);

    public static void main(String[] args) {
        logger.info("Start  SmartHomeApplication.");
        SpringApplication.run(SmartHomeApplication.class, args);
    }
}
