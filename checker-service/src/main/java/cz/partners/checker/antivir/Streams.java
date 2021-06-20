package cz.partners.checker.antivir;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Streams {

    String INPUT = "fileUploaded";
    String OUTPUT = "fileChecked";

    @Input(Streams.INPUT)
    SubscribableChannel input();

    @Output(Streams.OUTPUT)
    MessageChannel fileChecked();
}