package cz.partners.files.savefile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.MessageChannel;

import cz.partners.repository.savefile.FileEntity;
import cz.partners.repository.savefile.UploadedFilesRepository;

class UploadFileServiceTest {

    private UploadedFilesRepository repository;
    private UploadFileService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(UploadedFilesRepository.class);
        Streams streams = Mockito.mock(Streams.class);
        Mockito.when(streams.fileUploaded()).thenReturn(Mockito.mock(MessageChannel.class));
        service = new UploadFileService(repository, streams);
    }


    @Test
    void checkUploadFile() {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(UUID.randomUUID());
        Mockito.when(repository.save(Mockito.any(FileEntity.class))).thenReturn(fileEntity);

        UUID uuid = service.uploadFile("file.pdf", new byte[]{0, 1, 1, 0});
        assertEquals(fileEntity.getId(), uuid);
    }
}