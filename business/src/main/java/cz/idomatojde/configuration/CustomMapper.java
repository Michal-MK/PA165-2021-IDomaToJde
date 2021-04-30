package cz.idomatojde.configuration;

import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;

/**
 * Custom mapping for DTOs that Dozen package cannot convert
 *
 * @author Jiri Vrbka
 */
public class CustomMapper {

    public static OfferDTO toOfferDTO(Offer offer){
        var dto = new OfferDTO();
        dto.setId(offer.getId());
        dto.setOwner(toUserDTO(offer.getOwner()));
        dto.setTitle(offer.getTitle());
        dto.setDescription(offer.getDescription());
        dto.setPrice(offer.getPrice());
        dto.setCreatedDate(offer.getCreatedDate());
        dto.setExpirationDate(offer.getExpirationDate());
        dto.setCategory(offer.getCategory());
        dto.setCapacity(offer.getCapacity());
        dto.setRegistered(offer.getRegistered());

        return dto;
    }

    public static UserDTO toUserDTO(User user){
        var dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setCredits(user.getCredits());
        dto.setBonusCredits(user.getBonusCredits());
        dto.setWantsAdvertisement(user.wantsAdvertisement());
        dto.setAdmin(user.isAdmin());

        return dto;
    }

}
