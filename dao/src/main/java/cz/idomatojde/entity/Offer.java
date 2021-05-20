package cz.idomatojde.entity;

import cz.idomatojde.entity.base.IEntity;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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
 * An Entity representing a service offer
 *
 * @author Jiri Vrbka
 */
@Entity
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

    @OneToMany(mappedBy = "offer")
    private List<TimetableEntry> events = new ArrayList<>();

    @ManyToMany(mappedBy = "subscribedOffers")
    private List<User> subscribers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    private Integer capacity;

    private Integer registered;


    public void setId(Long id) {
        this.id = id;
    }

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

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
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
        if (o == null) return false;
        if (!(o instanceof Offer)) return false;

        Offer offer = (Offer) o;
        return Objects.equals(getOwner(), offer.getOwner())
                && Objects.equals(getTitle(), offer.getTitle())
                && Objects.equals(getDescription(), offer.getDescription())
                && Objects.equals(getPrice(), offer.getPrice())
                && Objects.equals(getCreatedDate(), offer.getCreatedDate())
                && Objects.equals(getExpirationDate(), offer.getExpirationDate())
                && getCategory() == offer.getCategory()
                && Objects.equals(getCapacity(), offer.getCapacity()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getTitle(),
                getPrice(),
                getCreatedDate(),
                getExpirationDate(),
                getCategory(),
                getCapacity()
        );
    }
}
