package cz.partners.repository.savefile;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadedFilesRepository extends JpaRepository <FileEntity, UUID>  {
    FileEntity findTopByStatusOrderByCreated(FileStatus status);
    Optional<FileEntity> findByIdAndStatus(UUID id, FileStatus status);
}
