package cz.partners.files.savefile;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Streams {

    String OUTPUT = "fileUploaded";

    @Output(OUTPUT)
    MessageChannel fileUploaded();
}