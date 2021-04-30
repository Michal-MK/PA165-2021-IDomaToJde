package cz.idomatojde.dto.offer;

import java.util.Objects;

/**
 * DTO representing offer description and title change
 *
 * @author Jiri Vrbka
 */
public class ChangeDescriptionOfferDTO {

    private Long id;

    private String title;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof ChangeDescriptionOfferDTO)) return false;

        ChangeDescriptionOfferDTO offer = (ChangeDescriptionOfferDTO) o;
        return Objects.equals(getTitle(), offer.getTitle())
                && Objects.equals(getDescription(), offer.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getTitle(),
                getDescription()
        );
    }
}
