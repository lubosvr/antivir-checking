package cz.partners.files.savefile;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import cz.partners.repository.savefile.FileEntity;
import cz.partners.repository.savefile.UploadedFilesRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final UploadedFilesRepository uploadedFilesRepository;
    private final Streams streams;

    @Transactional
    UUID uploadFile(String filename, byte[] content) {
        FileEntity entity = new FileEntity(filename, content);
        UUID id = uploadedFilesRepository.save(entity).getId();
        streams.fileUploaded().send(
                MessageBuilder.withPayload(id).build());
        return id;
    }
}
