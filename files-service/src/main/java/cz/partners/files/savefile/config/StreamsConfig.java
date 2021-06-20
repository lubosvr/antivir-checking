package cz.partners.files.savefile.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import cz.partners.files.savefile.Streams;

@Configuration
@PropertySource("classpath:rabbitmq-stream-bindings.properties")
@EnableBinding(Streams.class)
public class StreamsConfig {
}
