package cz.idomatojde.services.facades;

import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.facade.OfferFacade;
import cz.idomatojde.services.OfferService;
import cz.idomatojde.services.UserService;
import cz.idomatojde.services.base.MappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;

/**
 * @author Jiri Vrbka
 */
@Service
@Transactional
public class OfferFacadeImpl implements OfferFacade {

    @Inject
    private MappingService mappingService;

    @Inject
    private OfferService offerService;

    @Inject
    private UserService userService;

    @Override
    public long registerOffer(RegisterOfferDTO registerOfferDTO) {
        var offer = mappingService.mapTo(registerOfferDTO, Offer.class);
        var user = userService.getById(registerOfferDTO.getOwner().getId());
        offer.setOwner(user);
        offer.setCreatedDate(LocalDate.now());
        offer.setExpirationDate(LocalDate.now().plusDays(10));
        offerService.create(offer);
        return offer.getId();
    }

    @Override
    public OfferDTO getOfferWithId(long id) {
        var offer = offerService.getById(id);
        // dozen cannot convert this
        var dto = new OfferDTO();
        dto.setId(offer.getId());
        dto.setOwner(mappingService.mapTo(offer.getOwner(), UserDTO.class));
        dto.setTitle(offer.getTitle());
        dto.setDescription(offer.getDescription());
        dto.setPrice(offer.getPrice());
        dto.setCreatedDate(offer.getCreatedDate());
        dto.setExpirationDate(offer.getExpirationDate());
        //dto.setEvents(offer.getEvents());
        dto.setCategory(offer.getCategory());
        dto.setCapacity(offer.getCapacity());
        dto.setRegistered(offer.getRegistered());

        return dto;//mappingService.mapTo(offer, OfferDTO.class);
    }

    @Override
    public void removeOffer(long id) {
        var offer = offerService.getById(id);
        offerService.delete(offer);
    }

    @Override
    public void changeDescription(ChangeDescriptionOfferDTO changeDescriptionOfferDTO) {
        var offer = offerService.getById(changeDescriptionOfferDTO.getId());
        offer.setDescription(changeDescriptionOfferDTO.getDescription());
        offer.setTitle(changeDescriptionOfferDTO.getTitle());
    }
}
