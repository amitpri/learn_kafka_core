package com.amitpriyadarshi.learnkafka.tut1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //Create Producer properties
        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        Logger logger = LoggerFactory.getLogger(ProducerDemo.class);

        String topic_name = "tpc4";
        int num_records = 10000;

        int upper = 10;
        int lower = 1;

        //Create a Producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //Create a producer record
        for(int i = 0; i < num_records; i++){

            int r = (int) (Math.random() * ( upper - lower) + lower);
            String random_key = String.valueOf(r);

            String value = "Messages =>" + Integer.toString(i);
            String key = "thousand_id_" + random_key;

            ProducerRecord<String,String> record = new ProducerRecord<String, String>(topic_name, key ,value);
        //    producer.send(record);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if ( e == null){
                        logger.info("Message: " + key + ":" + value + "\n"
                                + "Offset:" + recordMetadata.offset() + "\n"
                                + "Timestamp:" + recordMetadata.timestamp());
                    }else{
                        logger.error("Error while producing", e);
                    }
                }
            }).get();
        }

    // Creating one separate record for closing the previous segment
        String key = "id_" + String.valueOf(upper + 1);
        ProducerRecord<String,String> record_last = new ProducerRecord<String, String>(topic_name, key ,"last message for closing the segment");
        producer.send(record_last);
        producer.close();

    }
}
