package cz.idomatojde.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@SuppressWarnings("JpaDataSourceORMInspection")
@Table(name = "\"User\"")
public class User {

    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
