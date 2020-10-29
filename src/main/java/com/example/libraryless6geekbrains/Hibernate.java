package com.example.libraryless6geekbrains;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Hibernate {
    @Bean
    public Module hibernate5Module()
    {
        return new Hibernate5Module();
    }
}
