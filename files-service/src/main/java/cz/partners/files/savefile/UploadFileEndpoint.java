package cz.partners.files.savefile;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UploadFileEndpoint {

    private final UploadFileService uploadFileService;

    @PostMapping("/uploadFile")
    public @ResponseBody UUID uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return uploadFileService.uploadFile(file.getOriginalFilename(), file.getBytes());
    }
}
