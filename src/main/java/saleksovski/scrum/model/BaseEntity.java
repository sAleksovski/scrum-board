package saleksovski.scrum.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by stefan on 1/14/16.
 */
@MappedSuperclass
public abstract class BaseEntity<ID> implements Serializable {

    @Column(name = "creation_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime creationTime;

    @Column(name = "modification_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime modificationTime;

    @Version
    private long version;

    public abstract ID getId();

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getModificationTime() {
        return modificationTime;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.creationTime = now;
        this.modificationTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationTime = LocalDateTime.now();
    }
}
