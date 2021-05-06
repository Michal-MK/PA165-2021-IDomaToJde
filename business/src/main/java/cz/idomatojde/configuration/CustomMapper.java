package cz.idomatojde.configuration;

import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;

import java.util.stream.Collectors;

/**
 * Custom mapping for DTOs that Dozer package cannot convert
 *
 * @author Jiri Vrbka
 */
public class CustomMapper {


    public static OfferDTO toOfferDTO(Offer offer) {
        return toOfferDTO(offer, true);
    }

    private static OfferDTO toOfferDTO(Offer offer, boolean withDependencies) {
        var dto = new OfferDTO();
        dto.setId(offer.getId());
        dto.setTitle(offer.getTitle());
        dto.setDescription(offer.getDescription());
        dto.setPrice(offer.getPrice());
        dto.setCreatedDate(offer.getCreatedDate());
        dto.setExpirationDate(offer.getExpirationDate());
        dto.setCategory(toCategoryDTO(offer.getCategory()));
        dto.setCapacity(offer.getCapacity());
        dto.setRegistered(offer.getRegistered());

        if (withDependencies) {
            dto.setOwner(toUserDTO(offer.getOwner()));
        }

        return dto;
    }

    private static CategoryDTO toCategoryDTO(Category category) {
        var dto = new CategoryDTO();
        dto.setName(category.getName());

        return dto;
    }

    private static UserDTO toUserDTO(User user) {
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

    public static TimetableEntryDTO toTimetableEntryDTO(TimetableEntry entry) {
        return toTimetableEntryDTO(entry, true);
    }

    private static TimetableEntryDTO toTimetableEntryDTO(TimetableEntry entry, boolean withDependencies) {
        var dto = new TimetableEntryDTO();

        dto.setId(entry.getId());
        dto.setEntryStart(entry.getEntryStart());
        dto.setLength(entry.getLength());
        dto.setDescription(entry.getDescription());
        dto.setDay(entry.getDay());

        if (withDependencies) {
            dto.setTimetable(toTimetableDTO(entry.getTimetable(), false));
            dto.setOffer(toOfferDTO(entry.getOffer(), false));
            dto.setMessages(entry.getMessages().stream().map(CustomMapper::toTimetableChatMessageDTO).collect(Collectors.toList()));
        }

        return dto;
    }

    public static TimetableChatMessageDTO toTimetableChatMessageDTO(TimetableChatMessage msg) {
        var dto = new TimetableChatMessageDTO();

        dto.setId(msg.getId());
        dto.setUserId(msg.getSender().getId());
        dto.setTimetableEntryId(msg.getTimetableEntry().getId());
        dto.setText(msg.getText());

        return dto;
    }

    public static TimetableDTO toTimetableDTO(Timetable table) {
        return toTimetableDTO(table, true);
    }

    private static TimetableDTO toTimetableDTO(Timetable table, boolean withDependencies) {
        var info = new UserContactInfoDTO();
        info.setEmail(table.getUser().getEmail());
        info.setName(table.getUser().getName());
        info.setSurname(table.getUser().getSurname());
        info.setPhoneNumber(table.getUser().getPhoneNumber());

        var dto = new TimetableDTO();
        dto.setId(table.getId());
        dto.setUserInfo(info);
        dto.setYear(table.getYear());
        dto.setWeek(table.getWeek());

        if (withDependencies) {
            dto.setEntries(table.getEntries().stream().map(CustomMapper::toTimetableEntryDTO).collect(Collectors.toList()));
        }

        return dto;
    }
}
