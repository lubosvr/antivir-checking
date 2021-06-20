package cz.partners.repository.savefile;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cz.partners.repository.config.JpaConfig;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {JpaConfig.class})
@Transactional(Transactional.TxType.NOT_SUPPORTED)
class UploadedFilesRepositoryTest {

    @Autowired
    private UploadedFilesRepository repository;

    @Test
    void checkInsert() {
        long before = repository.count();
        FileEntity fileEntity = fileEntity(FileStatus.PENDING);
        FileEntity save = repository.save(fileEntity);

        long after = repository.count();
        Assertions.assertNotNull(save.getId());
        Assertions.assertTrue(after > before);
    }

    @Test
    void checkFindByStatus() {
        FileEntity pending = repository.save(fileEntity(FileStatus.PENDING));
        FileEntity checked = repository.save(fileEntity(FileStatus.CHECKED));

        Assertions.assertEquals(pending, repository.findTopByStatusOrderByCreated(FileStatus.PENDING));
        Assertions.assertEquals(checked, repository.findTopByStatusOrderByCreated(FileStatus.CHECKED));
    }

    @Test
    void checkFindByIdAndStatus() {
        FileEntity save = repository.save(fileEntity(FileStatus.ERROR));
        Optional<FileEntity> found = repository.findByIdAndStatus(save.getId(), FileStatus.ERROR);

        Assertions.assertEquals(save, found.get());

        found = repository.findByIdAndStatus(save.getId(), FileStatus.PENDING);
        Assertions.assertFalse(found.isPresent());

        found = repository.findByIdAndStatus(UUID.randomUUID(), FileStatus.ERROR);
        Assertions.assertFalse(found.isPresent());
    }

    private FileEntity fileEntity(FileStatus status) {
        FileEntity fileEntity = new FileEntity("file.pdf", new byte[] {0x0, 0x1});
        fileEntity.setStatus(status);
        return fileEntity;
    }
}