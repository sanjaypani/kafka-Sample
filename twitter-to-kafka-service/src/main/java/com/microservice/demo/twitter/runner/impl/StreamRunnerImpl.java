package com.microservice.demo.twitter.runner.impl;

import com.microservice.demo.config.TwitterToKafkaConfigData;
import com.microservice.demo.twitter.listener.TwitterToKafkaStatusListener;
import com.microservice.demo.twitter.runner.StreamRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@Component
@Slf4j
public class StreamRunnerImpl implements StreamRunner {

    private TwitterToKafkaConfigData twitterToKafkaConfigData;

    private TwitterToKafkaStatusListener twitterToKafkaStatusListener;

    private TwitterStream twitterStream;

    StreamRunnerImpl(TwitterToKafkaConfigData twitterToKafkaConfigData,
                     TwitterToKafkaStatusListener twitterToKafkaStatusListener){

        this.twitterToKafkaConfigData = twitterToKafkaConfigData;
        this.twitterToKafkaStatusListener = twitterToKafkaStatusListener;

    }

    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterToKafkaStatusListener);

        final String[] keyWords = twitterToKafkaConfigData.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keyWords);
        twitterStream.filter(filterQuery);
        log.info("Started Filetring twitter Stream for keyword {} ", Arrays.toString(keyWords));

    }

    @PreDestroy
    public void shutdown() {
        if(twitterStream != null) {
            log.info("Closing Twitter Stream .....");
            twitterStream.shutdown();
        }
    }
}
