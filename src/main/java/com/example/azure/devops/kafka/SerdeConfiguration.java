package com.example.azure.devops.kafka;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.Data;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
/**
 * https://docs.confluent.io/platform/current/streams/developer-guide/datatypes.html
 */
public class SerdeConfiguration <K extends SpecificRecord, V extends SpecificRecord>{

    @Value("schema.registry.url")
    String url;

    private SpecificAvroSerde<K> kSerde;
    private SpecificAvroSerde<V> vSerde;

    private KafkaAvroDeserializer valueDeserializer;

    public SerdeConfiguration() {

        kSerde = new SpecificAvroSerde();
        kSerde.configure(Map.of("schema.registry.url", url), true);
        vSerde = new SpecificAvroSerde();
        vSerde.configure(Map.of("schema.registry.url", url), false);
    }
}
