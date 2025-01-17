/*
 * AuthenticatedAnnouncementRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.offer;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.group.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("select o from Offer o where o.id = :id")
	Offer findOneOfferById(int id);

	@Query("select o from Offer o")
	Collection<Offer> findAllOffers();

	@Query("select sc.currency from SystemConfiguration sc")
	Collection<String> findAllCurrencySystemConfiguration();

	@Query("select o from Offer o where o.availabilityPeriodStartDate < :moment and o.availabilityPeriodEndDate > :moment and o.id = :id")
	Offer findActiveOfferById(Date moment, int id);

}
