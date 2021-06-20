package cz.partners.checker.antivir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.MessageChannel;

import cz.partners.repository.savefile.FileEntity;
import cz.partners.repository.savefile.FileStatus;

class CheckerServiceTest {

    private UploadFileService uploadFileService;
    private Antivir antivir;
    private Streams streams;
    private CheckerService checkerService;

    @BeforeEach
    void setUp() {
        uploadFileService = mock(UploadFileService.class);
        antivir = mock(Antivir.class);
        streams = mock(Streams.class);
        when(streams.fileChecked()).thenReturn(mock(MessageChannel.class));
        checkerService = new CheckerService(uploadFileService, antivir, streams);
    }

    @Test
    void checkHandlingWithSuccess() throws CanNotCheckException {
        UUID uuid = UUID.randomUUID();
        FileEntity fileEntity = fileEntity(uuid);

        when(uploadFileService.getFilesById(uuid)).thenReturn(Optional.of(fileEntity));
        when(antivir.check(Mockito.any())).thenReturn(true);
        checkerService.handle(uuid);

        assertEquals(FileStatus.CHECKED, fileEntity.getStatus());
    }

    @Test
    void checkHandlingWithVirus() throws CanNotCheckException {
        UUID uuid = UUID.randomUUID();
        FileEntity fileEntity = fileEntity(uuid);

        when(uploadFileService.getFilesById(uuid)).thenReturn(Optional.of(fileEntity));
        when(antivir.check(Mockito.any())).thenReturn(false);
        checkerService.handle(uuid);

        assertEquals(FileStatus.ERROR, fileEntity.getStatus());
    }

    @Test
    void checkHandlingWithException() throws CanNotCheckException {
        UUID uuid = UUID.randomUUID();
        FileEntity fileEntity = fileEntity(uuid);

        when(uploadFileService.getFilesById(uuid)).thenReturn(Optional.of(fileEntity));
        when(antivir.check(Mockito.any())).thenThrow(new CanNotCheckException(new Exception()));
        checkerService.handle(uuid);

        assertEquals(FileStatus.PENDING, fileEntity.getStatus());
    }

    private FileEntity fileEntity(UUID uuid) {
        FileEntity fileEntity = new FileEntity("file.pdf", new byte[] {0});
        fileEntity.setId(uuid);
        fileEntity.setStatus(FileStatus.PENDING);
        return fileEntity;
    }
}