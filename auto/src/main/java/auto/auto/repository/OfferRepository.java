package auto.auto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.auto.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Integer> {

}
