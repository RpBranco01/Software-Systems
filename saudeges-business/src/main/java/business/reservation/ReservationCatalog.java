package business.reservation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ReservationCatalog {
	/**
	 * Entity manager for accessing the persistence service
	 */
	@PersistenceContext
	private EntityManager em;


	/**
	 * Creates a regular activity with the given data
	 * 
	 * @param TODO:COMPLETEME
	 */
	public void addReservation(Reservation res) {	
		em.persist(res);
	}
}
