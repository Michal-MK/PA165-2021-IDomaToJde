package cz.idomatojde.services.base;

import cz.idomatojde.dto.base.DurationDTO;
import cz.idomatojde.dto.base.LocalTimeDTO;
import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.dto.user.RegisterUserDTO;
import cz.idomatojde.dto.user.UserContactInfoDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.Timetable;
import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import cz.idomatojde.entity.base.IEntity;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Custom implementation of a mapper service, the methods available are self explanatory
 * every property that is present in both classes is mapped
 *
 * @author Michal Hazdra
 */
public interface MappingService {
    OfferDTO toOfferDTO(Offer offer);

    CategoryDTO toCategoryDTO(Category category);

    UserDTO toUserDTO(User user);

    UserContactInfoDTO toUserContactInfoDTO(User user);

    TimetableEntryDTO toTimetableEntryDTO(TimetableEntry entry);

    TimetableEntryDTO toTimetableEntryNoTimetableDTO(TimetableEntry entry);

    TimetableChatMessageDTO toTimetableChatMessageDTO(TimetableChatMessage msg);

    TimetableDTO toTimetableDTO(Timetable table);

    LocalTimeDTO toLocalTimeDTO(LocalTime time);

    DurationDTO toDurationDTO(Duration duration);

    Duration fromDurationDTO(DurationDTO time);

    LocalTime fromLocalTimeDTO(LocalTimeDTO time);

    User fromUserDto(UserDTO dto);

    User fromRegisterUserDto(RegisterUserDTO dto);

    Category fromCategoryDto(CategoryDTO dto);

    TimetableChatMessage fromTimetableChatMessageDto(TimetableChatMessageDTO dto);

    TimetableChatMessage fromRegisterTimetableChatMessageDto(AddTimetableChatMessageDTO dto);

    Timetable fromTimetableDto(TimetableDTO dto);

    Timetable fromRegisterTimetableDto(AddTimetableDTO dto);

    TimetableEntry fromTimetableEntryDto(TimetableEntryDTO dto);

    TimetableEntry fromTimetableEntryNoTimetableDto(TimetableEntryDTO dto);

    Offer fromOfferDto(OfferDTO dto);

    Offer fromRegisterOfferDto(RegisterOfferDTO dto);

    /**
     * A shortcut for mapping based on the {@link Class<TEntity>} to obtain the specific {@link IEntity} implementation
     * @param dto the DTO object
     * @param cls the class type to map to
     * @param <TEntity> generic type specifying all entity classes
     * @return the mapped entity implementation
     */
    <TEntity extends IEntity> TEntity mapDto(Object dto, Class<TEntity> cls);

    /**
     * A shortcut for mapping based on the {@link Class<TDto>} to obtain the specific DTO implementation
     * @param entity the entity object
     * @param cls the class type to map to
     * @param <TDto> generic type specifying all DTO classes
     * @return the mapped DTO implementation
     */
    <TDto> TDto mapEntity(Object entity, Class<TDto> cls);
}
