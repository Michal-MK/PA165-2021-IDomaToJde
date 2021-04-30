package cz.idomatojde.dto.offer;

import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for offer creation
 *
 * @author Jiri Vrbka
 */
public class RegisterOfferDTO {

    private UserDTO owner;

    private String title;

    private String description;

    private BigDecimal price;

    private LocalDate createdDate;

    private LocalDate expirationDate;

    private List<TimetableEntryDTO> events = new ArrayList<>();

    private Category category;

    private Integer capacity;

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
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

    public List<TimetableEntryDTO> getEvents() {
        return events;
    }

    public void setEvents(List<TimetableEntryDTO> events) {
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

}
