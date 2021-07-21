package com.microservice.demo.twitter;

import com.microservice.demo.config.TwitterToKafkaConfigData;
import com.microservice.demo.twitter.runner.StreamRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservice.demo")
@Slf4j
public class TwitterToKafkaApplication implements CommandLineRunner {

    private TwitterToKafkaConfigData twitterToKafkaConfigData;

    private final StreamRunner streamRunner;

    TwitterToKafkaApplication(TwitterToKafkaConfigData twitterToKafkaConfigData, StreamRunner streamRunner) {
        this.twitterToKafkaConfigData = twitterToKafkaConfigData;
        this.streamRunner = streamRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("App Strated ...");
        log.info(Arrays.toString(twitterToKafkaConfigData.getTwitterKeywords().toArray(new String[] {})));
        streamRunner.start();

    }
}
