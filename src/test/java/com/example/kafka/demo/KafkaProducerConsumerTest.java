package com.example.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rnorth.ducttape.unreliables.Unreliables;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;


public class KafkaProducerConsumerTest {

    private static Network network = Network.newNetwork();

    private static KafkaContainer kafka = new KafkaContainer("4.1.2").withNetwork(network);

    private static KafkaProducer<String, String> producer = null;

    private static KafkaConsumer<String, String> consumer = null;

    @BeforeClass
    public static void startContainers() {
        //SetUp all services infrastructure
        Startables.deepStart(Stream.of(kafka)).join();

        //Instantiate Global Producer and Consumer
            producer = new KafkaProducer<>(
                    ImmutableMap.of(
                            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers(),
                            ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString()
                    ),
                    new StringSerializer(),
                    new StringSerializer()
            );

            consumer = new KafkaConsumer<>(
                    ImmutableMap.of(
                            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers(),
                            ConsumerConfig.GROUP_ID_CONFIG, "tc-" + UUID.randomUUID(),
                            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                    ),
                    new StringDeserializer(),
                    new StringDeserializer()
            );
    }

    @Test
    public void testP2P_ProducerConsumer() throws Exception{

        String topicName = "messages";
        consumer.subscribe(Arrays.asList(topicName));

        producer.send(new ProducerRecord<>(topicName, "testcontainers", "rulezzz")).get();// get() makes sure that the message
        // made to the kafka

        Unreliables.retryUntilTrue(10, TimeUnit.SECONDS, () -> {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) {
                return false;
            }
            assertThat(records)
                    .hasSize(1)
                    .extracting(ConsumerRecord::topic, ConsumerRecord::key, ConsumerRecord::value)
                    .containsExactly(tuple(topicName, "testcontainers", "rulezzz"));
            return true;
        });
                consumer.unsubscribe();
    }
}
