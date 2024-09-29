package com.example.azure.devops.kafka;

import com.example.azure.devops.executor.dao.EmployeeDao;
import com.example.azure.devops.model.Employee;
import com.example.azure.devops.model.EmployeeKey;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.KafkaStreams;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final KafkaProperties kafkaProperties;
    private final EmployeeDao employeeDao;

    @Bean
    public SerdeConfiguration<EmployeeKey, Employee> employeeSerdeConfiguration() {
        return new SerdeConfiguration<>();
    }

    @Bean
    public KafkaStreams employeeStream(SerdeConfiguration<EmployeeKey, Employee> employeeSerdeConfiguration) {
        return KafkaStreamBuilder.builder()
                .withProperties(kafkaProperties)
                .build(EmployeeKey.class, Employee.class
                , (k, v) -> employeeDao.insertEntity(v) );
    }


}
