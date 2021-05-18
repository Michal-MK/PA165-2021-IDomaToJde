package cz.idomatojde.services.facades;

import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.facade.OfferFacade;
import cz.idomatojde.services.CategoryService;
import cz.idomatojde.services.OfferService;
import cz.idomatojde.services.UserService;
import cz.idomatojde.services.base.MappingService;
import cz.idomatojde.services.facades.base.BaseFacadeImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author Jiri Vrbka
 */
@Service
@Transactional
public class OfferFacadeImpl extends BaseFacadeImpl<RegisterOfferDTO, OfferDTO, Offer> implements OfferFacade {

    private final OfferService offerService;

    private final UserService userService;

    private final CategoryService categoryService;

    @Inject
    public OfferFacadeImpl(MappingService mappingService, OfferService offerService,
                           UserService userService, CategoryService categoryService) {
        super(offerService, mappingService, OfferDTO.class, Offer.class);
        this.offerService = offerService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void changeDescription(ChangeDescriptionOfferDTO changeDescriptionOfferDTO) {
        var offer = offerService.getById(changeDescriptionOfferDTO.getId());
        offer.setDescription(changeDescriptionOfferDTO.getDescription());
        offer.setTitle(changeDescriptionOfferDTO.getTitle());
    }
}
