package hr.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Apartment {
	
	private int id;
	private int baseCapacity;
	private int additionalCapacity;
	private String name;
	private String internalName;
	private String apartmentNote;
	
	HashMap<LocalDate, Reservation> reservedDatesMap = new LinkedHashMap<>();
	List<Reservation> reservationList = new ArrayList<>();
	List<LocalDate> reservedDates;
	LinkedHashMap<LocalDate, Double> prices = new LinkedHashMap<>();
	
	public Apartment(int id) {
		super();
		this.id = id;
	}

	

	public Apartment(int id, String name, String internalName,
			int baseCapacity, int additionalCapacity, String note, List<PricePerNight> pricesList) {
		super();
		this.id = id;
		this.name = name;
		this.internalName = internalName;
		this.baseCapacity = baseCapacity;
		this.additionalCapacity = additionalCapacity;
		this.apartmentNote = note;
		
		for(PricePerNight ppn : pricesList) {
			addPriceToPrices(ppn.getFrom(), ppn.getTo(), ppn.getPrice());
		}
	}
	
	public Apartment(int id, String name, String internalName,
			int baseCapacity, int additionalCapacity, String note) {
		super();
		this.id = id;
		this.name = name;
		this.internalName = internalName;
		this.baseCapacity = baseCapacity;
		this.additionalCapacity = additionalCapacity;
		this.apartmentNote = note;
		
	}

	public Apartment(int id, String name, Reservation ... r) {
		this.id = id;
		this.name = name;
		this.reservationList = Arrays.asList(r);
		
		reservedDates = new LinkedList<>();
		reservedDatesMap = new HashMap<>();
		
	}

	
	public void addToReservationList (Reservation r) {
		System.out.println("došta");
		try{
			System.out.println(reservationList.add(r));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(LocalDate date : r.getReservedDates()) {
			reservedDatesMap.put(date, r);
		}
	}
	
	private void addPriceToPrices(LocalDate from, LocalDate to, double price) {
		for(LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
			prices.put(date, price);
		}
	}
	
	public double getTotalPriceInDateRange(LocalDate from, LocalDate to) {
		List<LocalDate> dates = from.datesUntil(to).collect(Collectors.toList());
		double sum = 0.f;
		for(LocalDate date : dates) {
			if(prices.get(date) != null)
				sum += prices.get(date);
		}		
		return sum;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}

	public int getBaseCapacity() {
		return baseCapacity;
	}



	public void setBaseCapacity(int baseCapacity) {
		this.baseCapacity = baseCapacity;
	}



	public int getAdditionalCapacity() {
		return additionalCapacity;
	}



	public void setAdditionalCapacity(int additionalCapacity) {
		this.additionalCapacity = additionalCapacity;
	}



	public String getInternalName() {
		return internalName;
	}



	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}



	public String getApartmentNote() {
		return apartmentNote;
	}



	public void setApartmentNote(String apartmentNote) {
		this.apartmentNote = apartmentNote;
	}



	public List<Reservation> getReservationList() {
		return reservationList;
	}


	public boolean isDateReserved(LocalDate date) {
		return reservedDatesMap.containsKey(date);
	}

	public void setName(String name) {
		this.name = name;
	}



	public HashMap<LocalDate, Reservation> getReservedDatesMap() {
		return reservedDatesMap;
	}
	
	public void reserveDates() {
		for(Reservation reservation : reservationList) {
			reservedDates.addAll(reservation.getReservedDates());	
		}
	}
	
	public boolean reservationStatus(LocalDate date) {
		if(reservedDates.contains(date))
			return true;
		return false;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof Apartment) {
			Apartment otherApp = (Apartment) other;
			if(this.getId() == otherApp.getId())
				return true;
			return false;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Integer.hashCode(this.id);
	}
}
