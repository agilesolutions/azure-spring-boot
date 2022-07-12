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
public class KafkaStreamBuilder {
	private Map<String, Object> propConfig;
	private Properties props;
	private SerdeConfig serdeConfig;
	private String topic;
	private KafkaStreamBuilder() {
		this.propConfig = new HashMap<>();
		this.props = new Properties();
		this.topic = "";
	}
	public static KafkaStreamBuilder builder() {
		return new KafkaStreamBuilder();
	}
	public KafkaStreamBuilder withProperties(final KafkaConfigInterface config) {
		return this;
	}
	public KafkaStreamBuilder withSerdeConfig(final SerdeConfig serdeConfig) {
		this.serdeConfig = serdeConfig;
		return this;
	}
	public KafkaStreamBuilder withTopic(final String topic) {
		this.topic = topic;
		return this;
	}
	public <K extends SpecificRecordBase, V extends SpecificRecordBase> KafkaStreams build(Class<K> keyClazz,
		StreamsBuilder streamBuilder = new StreamsBuilder();
		KStream<K, V> igrStream = streamBuilder.stream(topic,
													   Consumed.with(serdeConfig.getKeySerde(),
																	 serdeConfig.getValueSerde()));
		igrStream.foreach(action);
		KafkaStreams kafkaStreams = new KafkaStreams(streamBuilder.build(), props);
		kafkaStreams.start();
		return kafkaStreams;
	}
}
public class KafkaConsumerBuilder {
    private Map<String, Object> propConfig;
    private List<String> topics;
    private KafkaConsumerBuilder() {
        this.propConfig = new HashMap<>();
        this.topics = new ArrayList<>();
    }
    public static KafkaConsumerBuilder builder() {
        return new KafkaConsumerBuilder();
    }
    public KafkaConsumerBuilder withBasicProperties(final KafkaConfigInterface config) {
        return this;
    }
    public KafkaConsumerBuilder withSpecificProperty(final String propertyName, final Object propertyValue) {
        propConfig.put(propertyName, propertyValue);
        return this;
    }
    public KafkaConsumerBuilder withSpecificProperties(final Map<? extends String, Object> specificProperties) {
        propConfig.putAll(specificProperties);
        return this;
    }
    public KafkaConsumerBuilder withGroupId(final String groupId) {
        propConfig.put(GROUP_ID_CONFIG, groupId);
        return this;
    }
    public KafkaConsumerBuilder withDefaultDeserializers() {
        propConfig.putAll(getDeserializers());
        return this;
    }
    public KafkaConsumerBuilder withDeserializers(final SerdeConfig serdeConfig) {
        propConfig.put(KEY_DESERIALIZER_CLASS_CONFIG, serdeConfig.getKeySerde().deserializer().getClass());
        propConfig.put(VALUE_DESERIALIZER_CLASS_CONFIG, serdeConfig.getValueSerde().deserializer().getClass());
        return this;
    }
    public KafkaConsumerBuilder subscribeToTopics(final List<String> subscribeTopics) {
        this.topics.addAll(subscribeTopics);
        return this;
    }
    public <K extends SpecificRecordBase, V extends SpecificRecordBase> KafkaConsumer<K, V> build(Class<K> keyClazz,
            Class<V> valueClazz) {
        validateKeyAndValueClass(keyClazz, valueClazz);
        KafkaConsumer<K, V> consumer = new KafkaConsumer<>(propConfig);
        consumer.subscribe(topics);
        return consumer;
    }
    @SuppressWarnings("squid:S1452")
    public KafkaConsumer<? extends SpecificRecordBase, ? extends SpecificRecordBase> build() {
        KafkaConsumer<? extends SpecificRecordBase, ? extends SpecificRecordBase> consumer = new KafkaConsumer<>(propConfig);
        consumer.subscribe(topics);
        return consumer;
    }
    private <K, V> void validateKeyAndValueClass(Class<K> keyClazz, Class<V> valueClazz) {
        if (keyClazz != SpecificRecordBase.class && !keyClazz.getClass().isInstance(SpecificRecordBase.class)) {
            throw new IllegalStateException(format("Key class {0} is not supported for igr kafka consumer",
                    keyClazz.getSimpleName()));
        }
        if (valueClazz != SpecificRecordBase.class && !valueClazz.getClass().isInstance(SpecificRecordBase.class)) {
            throw new IllegalStateException(format("Value class {0} is not supported for igr kafka consumer",
                    valueClazz.getSimpleName()));
        }

    }
}
public class KafkaConsumerFactoryBuilder {
	private Map<String, Object> propConfig;
	private KafkaConsumerFactoryBuilder() {
		this.propConfig = new HashMap<>();
	}
	public static KafkaConsumerFactoryBuilder builder() {
		return new KafkaConsumerFactoryBuilder();
	}
	public KafkaConsumerFactoryBuilder withBasicProperties(final KafkaConfigInterface config) {
		propConfig.putAll(getSslProps(config));
		return this;
	}
	public KafkaConsumerFactoryBuilder withSpecificProperty(final String propertyName, final Object propertyValue) {
		return this;
	}
	public KafkaConsumerFactoryBuilder withSpecificProperties(final Map<? extends String, Object> specificProperties) {
		propConfig.putAll(specificProperties);
		return this;
	}
	public KafkaConsumerFactoryBuilder withGroupId(final String groupId) {
		propConfig.put(GROUP_ID_CONFIG, groupId);
		return this;
	}
	public KafkaConsumerFactoryBuilder withDefaultDeserializers() {
		propConfig.putAll(getDeserializers());
		return this;
	}
	public KafkaConsumerFactoryBuilder withDeserializersWithErrorHandling() {
		propConfig.putAll(getDeserializersWithErrorHandling());
		return this;
	}
	public <K, V> ConsumerFactory<K, V> build(Class<K> keyClazz,
											  Class<V> valueClazz) {
		return new DefaultKafkaConsumerFactory<>(propConfig);
	}
}
public class KafkaProducerBuilder {
    private Map<String, Object> propConfig;
    private KafkaProducerBuilder() {
        this.propConfig = new HashMap<>();
    }
    public static KafkaProducerBuilder builder() {
        return new KafkaProducerBuilder();
    }
    public KafkaProducerBuilder withBasicProperties(final KafkaConfigInterface config) {
        propConfig.putAll(getSslProps(config));
        return this;
    }
    public KafkaProducerBuilder withSpecificProperty(final String propertyName, final Object propertyValue) {
        return this;
    }
    public KafkaProducerBuilder withClientId(final String clientId) {
        propConfig.put(CLIENT_ID_CONFIG, clientId);
        return this;
    }
    public KafkaProducerBuilder withDefaultSerializers() {
        propConfig.putAll(getSerializers());
        return this;
    }
    public KafkaProducer build() {
        return new KafkaProducer(propConfig);
    }
    public <K extends SpecificRecordBase, V extends SpecificRecordBase> KafkaProducer<K, V> build(Class<K> keyClazz,
            Class<V> valueClazz) {
        return new KafkaProducer<>(propConfig);
    }
}
@Getter
public class SerdeConfig<K extends SpecificRecord, V extends SpecificRecord> {
    private SpecificAvroSerde<K> keySerde;
    private SpecificAvroSerde<V> valueSerde;
    public SerdeConfig(final KafkaConfigInterface config) {
        Map<String, String> serdeConfig = Map.of(SCHEMA_REGISTRY_URL, config.getSchemaRegistryUrl(),
                BASIC_AUTH_CREDENTIALS_SOURCE, config.getCredentialSource(),
                USER_INFO_CONFIG, config.getBasicAuthUserInfo());

        keySerde = new SpecificAvroSerde<>();
        keySerde.configure(serdeConfig, true);
        valueSerde = new SpecificAvroSerde<>();
        valueSerde.configure(serdeConfig, false);
    }
}
@Configuration
@EnableKafka
@AllArgsConstructor
public class KafkaConfig {
    private final PropertiesConfigCS config;
    @Bean
    public SerdeConfig<PersonKey, Person personConfig() {
        return new SerdeConfig<>(config);
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(KafkaConsumerFactoryBuilder.builder()
                .withBasicProperties(config)
                .withGroupId(UUID.randomUUID().toString())
                .withDeserializersWithErrorHandling()
                .withSpecificProperty(ENABLE_AUTO_COMMIT_CONFIG, false)
                .build(Integer.class, String.class));
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
    @Bean
    public KafkaStreams PersonStream(
            SerdeConfig<PersonKey, Person> personConfig) {
        return KafkaStreamBuilder.builder()
                .withProperties(config)
                .withSerdeConfig(personConfig)
                .withTopic(config.getProgramCodeCoverage())
                .build(PersonKey.class, Person.class,
                        (key, value) -> programCodeCoverageCache.cache(value));
    }
    @Bean
    public KafkaProducer kafkaProducer() {
        return KafkaProducerBuilder.builder()
                .withBasicProperties(config)
                .withClientId("personProducer")
                .withDefaultSerializers()
                .build();
    }
    @Bean
    public KafkaConsumer<PersonKey, Person> personConsumer(
            SerdeConfig<SdlCashFlowKey, SdlCashFlow> personConfig) {
        return KafkaConsumerBuilder.builder()
                .withBasicProperties(config)
                .withSpecificProperty(ENABLE_AUTO_COMMIT_CONFIG, true)
                .withDeserializers(cashFlowSerdeConfig)
                .withGroupId(config.getPersonConsumerGroup())
                .subscribeToTopic(config.getPersonOutputTopic())
                .build(PersonKey.class, Person.class);
    }

}


}
