package cz.idomatojde.dao;

import cz.idomatojde.entity.Offer;
import cz.idomatojde.entity.User;

import java.util.List;

/*
Created by Jiri Vrbka
 */
public interface OfferDao {
    public void create(Offer offer);
    public List<Offer> findAll();
    public List<Offer> findByUser(User u);
    public Offer findById(Long id);
    public void remove(Offer o)  throws IllegalArgumentException;
    public List<Offer> getActiveOffers();
}
