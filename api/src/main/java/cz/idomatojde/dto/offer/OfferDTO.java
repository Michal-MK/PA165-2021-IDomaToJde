package cz.idomatojde.dto.offer;

import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO representing whole {@link cz.idomatojde.entity.Offer}
 *
 * @author Jiri Vrbka
 */
public class OfferDTO {

    private Long id;

    private UserDTO owner;

    private String title;

    private String description;

    private BigDecimal price;

    private LocalDate createdDate;

    private LocalDate expirationDate;

    private List<TimetableEntryDTO> events = new ArrayList<>();

    private Category category;

    private Integer capacity;

    private Integer registered;


    public Long getId() {
        return id;
    }

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
        if (!(o instanceof OfferDTO)) return false;

        OfferDTO offer = (OfferDTO) o;
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
