package com.amitpriyadarshi.learnkafka.tut1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {

    public static void main(String[] args) {

        //Create Producer properties
        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        //Create a Producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //Create a producer record
        for(int i = 0; i < 1000000; i++){

            ProducerRecord<String,String> record = new ProducerRecord<String, String>("first_topic","ANDY" + i);
            producer.send(record);
        }

        //Send Data


        producer.close();

    }
}
