package cz.idomatojde.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Offer {

    @Id
    private Long id;

    @ManyToOne
    private User owner;

    private LocalDateTime expirationDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime epirationDate) {
        this.expirationDate = epirationDate;
    }
}
