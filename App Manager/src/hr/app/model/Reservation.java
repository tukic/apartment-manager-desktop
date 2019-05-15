package hr.app.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import hr.app.enumerations.ReservationStatus;

public class Reservation {

	private int id;
	private Tourists tourists;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private double pricePerNight;
	private double totalPrice;
	private ReservationStatus status;
	private double advancedPayment;

	public Reservation(int id, LocalDate checkInDate, LocalDate checkOutDate,
			double pricePerNight, double totalPrice, double advancedPayment,
			ReservationStatus status, Tourists tourists) {
		this.id = id;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.pricePerNight = pricePerNight;
		this.totalPrice = totalPrice;//* Math.toIntExact(ChronoUnit.DAYS.between(checkOutDate, checkInDate));
		this.advancedPayment = advancedPayment;
		this.status = status;
		this.tourists = tourists;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public Tourists getTourists() {
		return tourists;
	}

	public int getId() {
		return id;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public double getAdvancedPayment() {
		return advancedPayment;
	}

	public void setTourists(Tourists tourists) {
		this.tourists = tourists;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public void setAdvancedPayment(int advancedPayment) {
		this.advancedPayment = advancedPayment;
	}

	public List<LocalDate> getReservedDates() {
		return checkInDate.datesUntil(checkOutDate).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return this.tourists.getName();
	}

}
