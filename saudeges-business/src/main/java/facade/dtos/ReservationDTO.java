package facade.dtos;

import java.io.Serializable;
import java.util.Date;

import business.reservation.PaymentDetails;
import business.reservation.Reservation;

public class ReservationDTO implements Serializable{

	private int id;
	private Date date;
	private String email;
	private String entity;
	private String reference;
	private double price;
	
	/*private PaymentDetails paymentDetails;*/
	
	public ReservationDTO(int id, Date date, String email, PaymentDetails paymentDetails) {
		this.id = id;
		this.date = date;
		this.email = email;
		this.entity = paymentDetails.getEntity();
		this.reference = paymentDetails.getReference();
		this.price = paymentDetails.getPrice();
	}
	
	public ReservationDTO(Reservation r) {
		this.id = r.getID();
		this.date = r.getDate();
		this.email = r.getEmail();
		this.entity = r.getPaymentDetails().getEntity();
		this.reference = r.getPaymentDetails().getReference();
		this.price = r.getPaymentDetails().getPrice();
	}
	
	public int getID() {
		return this.id;
	}

	public Date getDate() {
		return this.date;
	}

	public String getEmail() {
		return this.email;
	}

	public String getEntity() {
		return entity;
	}

	public String getReference() {
		return reference;
	}

	public double getPrice() {
		return price;
	}
}
