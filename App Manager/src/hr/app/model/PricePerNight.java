package hr.app.model;

import java.time.LocalDate;

public class PricePerNight {

	LocalDate from;
	LocalDate to;
	double price;
	
	public PricePerNight(LocalDate from, LocalDate to, double price) {
		this.from = from;
		this.to = to;
		this.price = price;
	}

	public LocalDate getFrom() {
		return from;
	}

	public LocalDate getTo() {
		return to;
	}

	public double getPrice() {
		return price;
	}
	
}
