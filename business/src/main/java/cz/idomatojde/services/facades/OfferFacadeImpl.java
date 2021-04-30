package cz.idomatojde.services.facades;

import cz.idomatojde.configuration.CustomMapper;
import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
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
        return CustomMapper.toOfferDTO(offer);
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
