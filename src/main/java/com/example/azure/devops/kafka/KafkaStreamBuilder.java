package com.example.azure.devops.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KafkaStreamBuilder {

    private Map<String, Object> propertyConfig;
    private String topic;
    private Properties properties;

    private KafkaStreamBuilder() {
        this.properties = new Properties();
        this.propertyConfig = new HashMap<>();
    }

    public static KafkaStreamBuilder builder() {
        return new KafkaStreamBuilder();
    }

    public KafkaStreamBuilder withProperties(final KafkaConfiguration config) {

        propertyConfig.put("whatever",config.getExample());
        return this;
    }


    public KafkaStreamBuilder withTopic(final String topic) {

        this.topic = topic;
        return this;
    }


    public <K extends SpecificRecordBase, V extends SpecificRecordBase> KafkaStreams build(Class<K> key,
                                                                                           Class<V> value,
                                                                                           ForeachAction<K, V> action) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<K, V> stream = streamsBuilder.stream(topic,null);

        stream.foreach(action);

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);

        return kafkaStreams;



    }

}
