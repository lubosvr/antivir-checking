package cz.partners.checker.antivir;

import java.util.Optional;
import java.util.UUID;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cz.partners.repository.savefile.FileEntity;
import cz.partners.repository.savefile.FileStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckerService {

    private final UploadFileService uploadFileService;
    private final Antivir antivir;
    private final Streams streams;

    @Scheduled(fixedDelay = 30000)
    public void schedule() {
        log.debug("checking started");
        Optional<FileEntity> fileToCheck = uploadFileService.getFilesToCheck();
        fileToCheck.ifPresent(fileEntity -> handle(fileEntity.getId()));
        log.debug("checking finished");
    }

    @StreamListener(Streams.INPUT)
    public void handle(UUID id) {
        log.debug("handle {} starts", id);
        Optional<FileEntity> fileToCheck = uploadFileService.getFilesById(id);
        if (fileToCheck.isPresent()) {
            FileEntity fileEntity = fileToCheck.get();
            try {
                boolean result = antivir.check(fileEntity.getData());
                fileEntity.setStatus(result? FileStatus.CHECKED : FileStatus.ERROR);
            } catch (CanNotCheckException e) {
                // try next time
                fileEntity.setStatus(FileStatus.PENDING);
            }
            uploadFileService.save(fileEntity);
            streams.fileChecked().send(
                    MessageBuilder.withPayload(id).build());
        }
        log.debug("handle finished");
    }
}
