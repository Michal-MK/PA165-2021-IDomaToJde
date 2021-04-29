package cz.idomatojde.entity;

import cz.idomatojde.entity.base.IEntity;

import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * An Entity representing an offer for online lesson
 *
 * @author Jiri Vrbka
 */
@Entity
@SuppressWarnings("JpaDataSourceORMInspection")
@Table(name = "\"Offer\"")
public class Offer implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User owner;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private LocalDate createdDate;

    @NotNull
    private LocalDate expirationDate;

    @OneToMany
    private List<TimetableEntry> events = new ArrayList<>();

    @Enumerated
    private Category category;

    private Integer capacity;

    private Integer registered;


    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<TimetableEntry> getEvents() {
        return events;
    }

    public void setEvents(List<TimetableEntry> events) {
        this.events = events;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getRegistered() {
        return registered;
    }

    public void setRegistered(Integer registered) {
        this.registered = registered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (!(o instanceof Offer)) return false;

        Offer offer = (Offer) o;
        return Objects.equals(getOwner(), offer.getOwner())
                && Objects.equals(getTitle(), offer.getTitle())
                && Objects.equals(getDescription(), offer.getDescription())
                && Objects.equals(getPrice(), offer.getPrice())
                && Objects.equals(getCreatedDate(), offer.getCreatedDate())
                && Objects.equals(getExpirationDate(), offer.getExpirationDate())
                && Objects.equals(getEvents(), offer.getEvents())
                && getCategory() == offer.getCategory()
                && Objects.equals(getCapacity(), offer.getCapacity())
                && Objects.equals(getRegistered(), offer.getRegistered()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getTitle(),
                getDescription(),
                getPrice(),
                getCreatedDate(),
                getExpirationDate(),
                getEvents(),
                getCategory(),
                getCapacity(),
                getRegistered()
        );
    }
}
