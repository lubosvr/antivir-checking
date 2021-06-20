package cz.partners.checker.antivir;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cz.partners.repository.savefile.FileEntity;
import cz.partners.repository.savefile.FileStatus;
import cz.partners.repository.savefile.UploadedFilesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadFileService {

    private final UploadedFilesRepository uploadedFilesRepository;

    @Transactional
    Optional<FileEntity> getFilesToCheck() {
        return Optional.ofNullable(uploadedFilesRepository.findTopByStatusOrderByCreated(FileStatus.PENDING));
    }

    @Transactional
    Optional<FileEntity> getFilesById(UUID id) {
        Optional<FileEntity> fileEntity;
        try {
            fileEntity = uploadedFilesRepository.findByIdAndStatus(id, FileStatus.PENDING);
            fileEntity.ifPresent(entity -> entity.setStatus(FileStatus.CHECKING));
        } catch (Exception e) {
            log.error("File with id " + id + " not found!", e);
            fileEntity = Optional.empty();
        }
        return fileEntity;
    }


    @Transactional
    void save(FileEntity entity) {
        uploadedFilesRepository.save(entity);
    }
}
