package cz.idomatojde.services.base;

import cz.idomatojde.dto.base.DurationDTO;
import cz.idomatojde.dto.base.LocalTimeDTO;
import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.CreateTimetableEntryDTO;
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
import cz.idomatojde.services.CategoryService;
import cz.idomatojde.services.OfferService;
import cz.idomatojde.services.TimetableChatMessageService;
import cz.idomatojde.services.TimetableService;
import cz.idomatojde.services.UserService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;

/**
 * Custom mapping for DTOs that Dozer package cannot convert
 *
 * @author Michal Hazdra
 */
@Service
public class MappingServiceImpl implements MappingService {

    private final UserService users;
    private final OfferService offers;
    private final TimetableService timetables;
    private final TimetableChatMessageService chatMessages;
    private final CategoryService categories;

    public MappingServiceImpl(UserService users, OfferService offers, TimetableService timetables,
                              TimetableChatMessageService chatMessages, CategoryService categories) {
        this.users = users;
        this.offers = offers;
        this.timetables = timetables;
        this.chatMessages = chatMessages;
        this.categories = categories;
    }

    @Override
    public OfferDTO toOfferDTO(Offer offer) {
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
        dto.setOwner(toUserContactInfoDTO(offer.getOwner()));

        return dto;
    }

    @Override
    public CategoryDTO toCategoryDTO(Category category) {
        if (category == null) return null;

        var dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());

        return dto;
    }

    @Override
    public UserDTO toUserDTO(User user) {
        var dto = new UserDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setContactInfo(toUserContactInfoDTO(user));
        dto.setCredits(user.getCredits());
        dto.setBonusCredits(user.getBonusCredits());
        dto.setWantsAdvertisement(user.wantsAdvertisement());
        dto.setAdmin(user.isAdmin());

        return dto;
    }

    @Override
    public UserContactInfoDTO toUserContactInfoDTO(User user) {
        var dto = new UserContactInfoDTO();

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setPhoneNumber(user.getPhoneNumber());

        return dto;
    }

    @Override
    public TimetableEntryDTO toTimetableEntryDTO(TimetableEntry entry) {
        var dto = new TimetableEntryDTO();

        dto.setId(entry.getId());
        dto.setEntryStart(toLocalTimeDTO(entry.getEntryStart()));
        dto.setLength(toDurationDTO(entry.getLength()));
        dto.setDescription(entry.getDescription());
        dto.setDay(entry.getDay());
        dto.setTimetable(toTimetableDTO(entry.getTimetable()));
        dto.setOffer(toOfferDTO(entry.getOffer()));
        dto.setMessages(entry.getMessages().stream().map(this::toTimetableChatMessageDTO).collect(Collectors.toList()));

        return dto;
    }

    @Override
    public TimetableEntryDTO toTimetableEntryNoTimetableDTO(TimetableEntry entry) {
        var dto = new TimetableEntryDTO();

        dto.setId(entry.getId());
        dto.setEntryStart(toLocalTimeDTO(entry.getEntryStart()));
        dto.setLength(toDurationDTO(entry.getLength()));
        dto.setDescription(entry.getDescription());
        dto.setDay(entry.getDay());
        dto.setOffer(toOfferDTO(entry.getOffer()));
        dto.setMessages(entry.getMessages().stream().map(this::toTimetableChatMessageDTO).collect(Collectors.toList()));

        return dto;
    }

    @Override
    public TimetableChatMessageDTO toTimetableChatMessageDTO(TimetableChatMessage msg) {
        var dto = new TimetableChatMessageDTO();

        dto.setId(msg.getId());
        dto.setUserId(msg.getSender().getId());
        dto.setTimetableEntryId(msg.getTimetableEntry().getId());
        dto.setText(msg.getText());

        return dto;
    }

    @Override
    public TimetableDTO toTimetableDTO(Timetable table) {
        var info = new UserContactInfoDTO();
        info.setId(table.getUser().getId());
        info.setEmail(table.getUser().getEmail());
        info.setName(table.getUser().getName());
        info.setSurname(table.getUser().getSurname());
        info.setPhoneNumber(table.getUser().getPhoneNumber());

        var dto = new TimetableDTO();
        dto.setId(table.getId());
        dto.setUserInfo(info);
        dto.setYear(table.getYear());
        dto.setWeek(table.getWeek());

        dto.setEntries(table.getEntries().stream()
                .map(this::toTimetableEntryNoTimetableDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    @Override
    public LocalTimeDTO toLocalTimeDTO(LocalTime time) {
        LocalTimeDTO dto = new LocalTimeDTO();

        dto.setHour(time.getHour());
        dto.setMinute(time.getMinute());
        dto.setSecond(time.getSecond());

        return dto;
    }

    @Override
    public DurationDTO toDurationDTO(Duration duration) {
        DurationDTO dto = new DurationDTO();

        dto.setMinutes(duration.toMinutes());

        return dto;
    }

    @Override
    public Duration fromDurationDTO(DurationDTO time) {
        return Duration.ofMinutes(time.getMinutes());
    }

    @Override
    public LocalTime fromLocalTimeDTO(LocalTimeDTO time) {
        return LocalTime.of(time.getHour(), time.getMinute(), time.getSecond());
    }

    @Override
    public User fromUserDto(UserDTO dto) {
        User u = new User();

        u.setId(dto.getId());
        u.setUsername(dto.getUsername());
        u.setPassword(dto.getPassword());
        u.setEmail(dto.getContactInfo().getEmail());
        u.setName(dto.getContactInfo().getName());
        u.setSurname(dto.getContactInfo().getSurname());
        u.setPhoneNumber(dto.getContactInfo().getPhoneNumber());
        u.setCredits(dto.getCredits());
        u.setBonusCredits(dto.getBonusCredits());
        u.setWantsAdvertisement(dto.wantsAdvertisement());
        u.setAdmin(dto.isAdmin());

        return u;
    }

    @Override
    public User fromUserContactDto(UserContactInfoDTO dto) {
        return users.getById(dto.getId());
    }

    @Override
    public User fromRegisterUserDto(RegisterUserDTO dto) {
        User u = new User();

        u.setUsername(dto.getUsername());
        u.setPassword(dto.getPassword());
        u.setEmail(dto.getEmail());
        u.setName(dto.getName());
        u.setSurname(dto.getSurname());
        u.setPhoneNumber(dto.getPhoneNumber());
        return u;
    }

    @Override
    public Category fromCategoryDto(CategoryDTO dto) {
        var c = new Category();

        c.setId(dto.getId());
        c.setName(dto.getName());

        return c;
    }

    @Override
    public TimetableChatMessage fromTimetableChatMessageDto(TimetableChatMessageDTO dto) {
        var tcm = new TimetableChatMessage();

        tcm.setId(dto.getId());
        tcm.setSender(users.getById(dto.getId()));
        tcm.setTimetableEntry(timetables.findEntry(dto.getTimetableEntryId()));
        tcm.setText(dto.getText());

        return tcm;
    }

    @Override
    public TimetableChatMessage fromRegisterTimetableChatMessageDto(AddTimetableChatMessageDTO dto) {
        var tcm = new TimetableChatMessage();

        tcm.setSender(fromUserDto(dto.getSender()));
        tcm.setTimetableEntry(fromTimetableEntryNoTimetableDto(dto.getTimetableEntry()));
        tcm.setText(dto.getText());

        return tcm;
    }

    @Override
    public Timetable fromTimetableDto(TimetableDTO dto) {
        var t = new Timetable();

        t.setId(dto.getId());
        t.setUser(users.getById(dto.getUserInfo().getId()));
        t.setYear(dto.getYear());
        t.setWeek(dto.getWeek());

        t.setEntries(dto.getEntries().stream()
                .map(this::fromTimetableEntryDto)
                .collect(Collectors.toList()));

        return t;
    }

    @Override
    public Timetable fromRegisterTimetableDto(AddTimetableDTO dto) {
        var t = new Timetable();

        t.setUser(users.getById(dto.getUserId()));
        t.setYear(dto.getYear());
        t.setWeek(dto.getWeek());

        return t;
    }

    @Override
    public TimetableEntry fromTimetableEntryDto(TimetableEntryDTO dto) {
        var te = new TimetableEntry();

        te.setId(dto.getId());
        te.setEntryStart(fromLocalTimeDTO(dto.getEntryStart()));
        te.setLength(fromDurationDTO(dto.getLength()));
        te.setDescription(dto.getDescription());
        te.setDay(dto.getDay());
        te.setTimetable(fromTimetableDto(dto.getTimetable()));
        te.setOffer(fromOfferDto(dto.getOffer()));
        te.setMessages(dto.getMessages().stream()
                .map(this::fromTimetableChatMessageDto)
                .collect(Collectors.toList()));

        return te;
    }

    @Override
    public TimetableEntry fromRegisterTimetableEntryDto(CreateTimetableEntryDTO dto) {
        var te = new TimetableEntry();

        te.setEntryStart(fromLocalTimeDTO(dto.getEntryStart()));
        te.setLength(fromDurationDTO(dto.getLength()));
        te.setDescription(dto.getDescription());
        te.setDay(dto.getDay());
        te.setTimetable(fromTimetableDto(dto.getTimetable()));
        te.setOffer(fromOfferDto(dto.getOffer()));

        return te;
    }

    @Override
    public TimetableEntry fromTimetableEntryNoTimetableDto(TimetableEntryDTO dto) {
        var te = new TimetableEntry();

        te.setId(dto.getId());
        te.setEntryStart(fromLocalTimeDTO(dto.getEntryStart()));
        te.setLength(fromDurationDTO(dto.getLength()));
        te.setDescription(dto.getDescription());
        te.setDay(dto.getDay());
        te.setOffer(fromOfferDto(dto.getOffer()));
        te.setMessages(dto.getMessages().stream()
                .map(this::fromTimetableChatMessageDto)
                .collect(Collectors.toList()));

        return te;
    }

    @Override
    public Offer fromOfferDto(OfferDTO dto) {
        var o = new Offer();

        o.setId(dto.getId());
        o.setTitle(dto.getTitle());
        o.setDescription(dto.getDescription());
        o.setPrice(dto.getPrice());
        o.setCreatedDate(dto.getCreatedDate());
        o.setExpirationDate(dto.getExpirationDate());
        o.setCategory(fromCategoryDto(dto.getCategory()));
        o.setCapacity(dto.getCapacity());
        o.setRegistered(dto.getRegistered());
        o.setOwner(fromUserContactDto(dto.getOwner()));

        return o;
    }

    @Override
    public Offer fromRegisterOfferDto(RegisterOfferDTO dto) {
        var o = new Offer();

        o.setTitle(dto.getTitle());
        o.setDescription(dto.getDescription());
        o.setPrice(dto.getPrice());
        o.setCreatedDate(LocalDate.now());
        o.setExpirationDate(LocalDate.now().plusMonths(1)); // TODO Hello we do not have this??
        o.setCategory(fromCategoryDto(dto.getCategory()));
        o.setCapacity(dto.getCapacity());
        o.setRegistered(0);
        o.setOwner(fromUserDto(dto.getOwner()));

        return o;
    }

    @Override
    public <TEntity extends IEntity> TEntity mapDto(Object dto, Class<TEntity> cls) {
        if (cls == User.class) {
            if (dto instanceof RegisterUserDTO) {
                return (TEntity) fromRegisterUserDto((RegisterUserDTO) dto);
            } else {
                return (TEntity) fromUserDto((UserDTO) dto);
            }
        }
        if (cls == Offer.class) {
            if (dto instanceof RegisterOfferDTO) {
                return (TEntity) fromRegisterOfferDto((RegisterOfferDTO) dto);
            } else {
                return (TEntity) fromOfferDto((OfferDTO) dto);
            }
        }
        if (cls == Timetable.class) {
            if (dto instanceof AddTimetableDTO) {
                return (TEntity) fromRegisterTimetableDto((AddTimetableDTO) dto);
            } else {
                return (TEntity) fromTimetableDto((TimetableDTO) dto);
            }
        }
        if (cls == TimetableEntry.class) {
            if(dto instanceof  CreateTimetableEntryDTO){
                return (TEntity) fromRegisterTimetableEntryDto((CreateTimetableEntryDTO) dto);
            }
            else {
                return (TEntity) fromTimetableEntryDto((TimetableEntryDTO) dto);
            }
        }
        if (cls == TimetableChatMessage.class) {
            if (dto instanceof AddTimetableChatMessageDTO) {
                return (TEntity) fromRegisterTimetableChatMessageDto((AddTimetableChatMessageDTO) dto);
            } else {
                return (TEntity) fromTimetableChatMessageDto((TimetableChatMessageDTO) dto);
            }
        }
        if (cls == Category.class) {
            return (TEntity) fromCategoryDto((CategoryDTO) dto);
        }
        return null;
    }

    @Override
    public <TDto> TDto mapEntity(Object entity, Class<TDto> cls) {
        if (cls == UserDTO.class) {
            return (TDto) toUserDTO((User) entity);
        }
        if (cls == OfferDTO.class) {
            return (TDto) toOfferDTO((Offer) entity);
        }
        if (cls == TimetableDTO.class) {
            return (TDto) toTimetableDTO((Timetable) entity);
        }
        if (cls == TimetableEntry.class) {
            return (TDto) toTimetableEntryDTO((TimetableEntry) entity);
        }
        if (cls == TimetableChatMessageDTO.class) {
            return (TDto) toTimetableChatMessageDTO((TimetableChatMessage) entity);
        }
        if (cls == CategoryDTO.class) {
            return (TDto) toCategoryDTO((Category) entity);
        }
        return null;
    }
}
