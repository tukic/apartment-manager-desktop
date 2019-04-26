package hr.app.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hr.app.enumerations.ReservationStatus;

public class Reservation {

	private int id;
	private Tourists tourists;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private int pricePerNight;
	private int totalPrice;
	private ReservationStatus status;
	private int advancedPayment;

	public Reservation(int id, LocalDate checkInDate, LocalDate checkOutDate,
			int pricePerNight, int totalPrice, int advancedPayment,
			ReservationStatus status, Tourists tourists) {
		this.id = id;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.pricePerNight = pricePerNight;
		this.totalPrice = pricePerNight;//* Math.toIntExact(ChronoUnit.DAYS.between(checkOutDate, checkInDate));
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

	public int getPricePerNight() {
		return pricePerNight;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public int getAdvancedPayment() {
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
