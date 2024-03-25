package com.jyujyu.dayonetest;

import com.jyujyu.dayonetest.service.KafkaConsumerService;
import com.jyujyu.dayonetest.service.KafkaProducerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class KafkaConsumaerApplicationTests extends IntegrationTest {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // @MockBean : Spring Context를 로드했을 때 어떤 빈을 목킹하고 싶을 때 사용하는 어노테이션
    @MockBean
    private KafkaConsumerService kafkaConsumerService;

    @Test
    public void kafkaSendAndConsumeTest() {
        String topic = "test-topic";
        String expectValue = "expect-value";

        // when
        kafkaProducerService.send(topic, expectValue);

        // then
        var stringCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(kafkaConsumerService, Mockito.timeout(5000).times(1))
            .process(stringCaptor.capture());

        Assertions.assertEquals(expectValue, stringCaptor.getValue());
    }
}
