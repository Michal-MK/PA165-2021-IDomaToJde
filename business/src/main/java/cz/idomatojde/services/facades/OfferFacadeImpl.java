package cz.idomatojde.services.facades;

import cz.idomatojde.configuration.CustomMapper;
import cz.idomatojde.dto.offer.ChangeDescriptionOfferDTO;
import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.offer.RegisterOfferDTO;
import cz.idomatojde.entity.Offer;
import cz.idomatojde.facade.OfferFacade;
import cz.idomatojde.services.CategoryService;
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

    private final MappingService mappingService;

    private final OfferService offerService;

    private final UserService userService;

    private final CategoryService categoryService;

    @Inject
    public OfferFacadeImpl(MappingService mappingService, OfferService offerService,
                           UserService userService, CategoryService categoryService) {
        this.mappingService = mappingService;
        this.offerService = offerService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public long registerOffer(RegisterOfferDTO registerOfferDTO) {
        var offer = mappingService.mapTo(registerOfferDTO, Offer.class);
        var user = userService.getById(registerOfferDTO.getOwner().getId());
        var cat = categoryService.getById(registerOfferDTO.getCategory().getId());
        offer.setOwner(user);
        offer.setCategory(cat);
        offer.setCreatedDate(LocalDate.now());
        offer.setExpirationDate(LocalDate.now().plusDays(10));
        offerService.create(offer);
        return offer.getId();
    }

    @Override
    public OfferDTO getOfferWithId(long id) {
        var offer = offerService.getById(id);
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
