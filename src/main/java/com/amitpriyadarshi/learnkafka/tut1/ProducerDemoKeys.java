package com.amitpriyadarshi.learnkafka.tut1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemoKeys {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class);

        //Create Producer properties
        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        //Create a Producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //Create a producer record
        for(int i = 0; i < 5; i++){

            String topic = "first_topic";
            String value = "Hello all" + Integer.toString(i);
            String key = "id_" + Integer.toString(i);

            ProducerRecord<String,String> record = new ProducerRecord<String, String>(topic, key, value);

            logger.info("Key: " + key);

            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if ( e == null){
                        logger.info("Received new Metadata . \n"
                                        + "Topic: " + recordMetadata.topic() + "\n"
                                        + "Partition:" + recordMetadata.partition() + "\n"
                                        + "Offset:" + recordMetadata.offset() + "\n"
                                        + "Timestamp:" + recordMetadata.timestamp());
                     }else{
                        logger.error("Error while producing", e);
                    }
                }
            }).get();
        }

        //Send Data


        producer.close();

    }
}
