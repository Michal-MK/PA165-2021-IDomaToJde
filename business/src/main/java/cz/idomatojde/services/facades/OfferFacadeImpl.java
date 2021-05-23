package cz.idomatojde.services.facades;

import cz.idomatojde.dto.category.CategoryDTO;
import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.entity.Category;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;
import cz.idomatojde.facade.OfferFacade;
import cz.idomatojde.services.CategoryService;
import cz.idomatojde.services.OfferService;
import cz.idomatojde.services.UserService;
import cz.idomatojde.services.base.MappingService;
import cz.idomatojde.services.facades.base.BaseFacadeImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Hazdra
 */
@Service
@Transactional
public class OfferFacadeImpl extends BaseFacadeImpl<RegisterOfferDTO, OfferDTO, Offer> implements OfferFacade {

    private final OfferService offerService;

    @Inject
    public OfferFacadeImpl(MappingService mappingService, OfferService offerService) {
        super(offerService, mappingService, OfferDTO.class, Offer.class);
        this.offerService = offerService;
    }

    @Override
    public void changeDescription(ChangeDescriptionOfferDTO changeDescriptionOfferDTO) {
        var offer = offerService.getById(changeDescriptionOfferDTO.getId());
        offer.setDescription(changeDescriptionOfferDTO.getDescription());
        offer.setTitle(changeDescriptionOfferDTO.getTitle());
    }

    @Override
    public List<OfferDTO> getAllSubscribedBy(UserDTO user) {
        List<Offer> offers = offerService.getOffersSubscribedTo(mapService.mapDto(user, User.class));
        return mapService.mapEntityCollection(new ArrayList<>(offers), OfferDTO.class);
    }

    @Override
    public List<OfferDTO> getAllByCategory(CategoryDTO category) {
        List<Offer> offers = offerService.getOffersByCategory(mapService.mapDto(category, Category.class));
        return mapService.mapEntityCollection(new ArrayList<>(offers), OfferDTO.class);
    }
}
