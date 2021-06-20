package cz.partners.files.savefile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class UploadFileEndpointTest {

    private UploadFileService service;
    private UploadFileEndpoint endpoint;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(UploadFileService.class);
        endpoint = new UploadFileEndpoint(service);
    }

    @Test
    void checkUploadFile() throws IOException {
        UUID expectedUUID = UUID.randomUUID();
        Mockito.when(service.uploadFile(Mockito.any(), Mockito.any())).thenReturn(expectedUUID);

        MultipartFile multipartFile = new MockMultipartFile("file.pdf", new byte[]{0, 1, 1, 0});
        UUID uuid = endpoint.uploadFile(multipartFile);
        assertEquals(expectedUUID, uuid);
    }
}