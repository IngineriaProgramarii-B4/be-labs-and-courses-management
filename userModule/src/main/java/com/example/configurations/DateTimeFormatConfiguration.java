package com.example.configurations;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class DateTimeFormatConfiguration {

    // configs for Jackson ser/deser
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {

        return builder -> {
            builder.simpleDateFormat("dd.MM.yyyy HH:mm");
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        };
    }
}