package cz.partners.repository.savefile;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "uploaded_files")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class FileEntity {

    @Id
    @Column(name = "resource_id")
    @GeneratedValue(generator = "uuid2_generator")
    @GenericGenerator(name = "uuid2_generator", strategy = "uuid2")
    private UUID id;

    @Column
    private String filename;

    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] data;

    @Column
    @Enumerated(EnumType.STRING)
    private FileStatus status = FileStatus.PENDING;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modified;

    public FileEntity(String filename, byte[] data) {
        this.filename = filename;
        this.data = data;
    }
}
