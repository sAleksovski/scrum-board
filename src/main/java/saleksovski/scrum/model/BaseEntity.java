package saleksovski.scrum.model;

/**
 * Created by stefan on 1/14/16.
 */
//import org.hibernate.annotations.Type;
//import org.joda.time.DateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity<ID> {

//    @Column(name = "creation_time", nullable = false)
//    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
//    private DateTime creationTime;
//
//    @Column(name = "modification_time", nullable = false)
//    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
//    private DateTime modificationTime;

    @Version
    private long version;

    public abstract ID getId();

    //Other getters are omitted for the sake of clarity.

//    @PrePersist
//    public void prePersist() {
//        DateTime now = DateTime.now();
//        this.creationTime = now;
//        this.modificationTime = now;
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        this.modificationTime = DateTime.now();
//    }
}
