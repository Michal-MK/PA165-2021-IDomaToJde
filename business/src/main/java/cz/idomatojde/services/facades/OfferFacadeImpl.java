package cz.idomatojde.services.facades;

import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.facade.OfferFacade;
import cz.idomatojde.services.OfferService;
import cz.idomatojde.services.base.MappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

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

    @Override
    public long registerOffer(RegisterOfferDTO registerOfferDTO) {
        var offer = mappingService.mapTo(registerOfferDTO, Offer.class);
        offerService.create(offer);
        return offer.getId();
    }

    @Override
    public OfferDTO getOfferWithId(long id) {
        var offer = offerService.getById(id);
        return mappingService.mapTo(offer, OfferDTO.class);
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
