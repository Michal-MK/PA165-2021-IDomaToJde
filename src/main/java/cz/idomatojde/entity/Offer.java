package cz.idomatojde.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;


/*
Created by Jiri Vrbka
 */
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User owner;

    @NotNull
    private String title;

    @NotNull
    private String description;

    private int price;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @OneToMany
    private TimetableEntry event;

    private Category category;

    private Integer capacity;

    private Integer registered;


    public void setId(Long id) {
        this.id = id;
    }

    @Id
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public TimetableEntry getEvent() {
        return event;
    }

    public void setEvent(TimetableEntry event) {
        this.event = event;
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
}
