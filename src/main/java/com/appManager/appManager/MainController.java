package com.appManager.appManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.appManager.model.Apartment;
import com.appManager.model.Reservation;

import hr.app.enumerations.ReservationStatus;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/demo") // This means URL's start with /demo (after Application path)
public class MainController {
	@Autowired // This means to get the bean called userRepository
	// Which is auto-generated by Spring, we will use it to handle the data
	private ReservationRepository reservationRepository;
	
	@Autowired
	private ApartmentRepository apartmentRepository;

	@PostMapping(path = "/add") // Map ONLY POST Requests
	public @ResponseBody String addNewReservation(@RequestParam int reservationId, @RequestParam LocalDate checkInDate,
			@RequestParam LocalDate checkOutDate, @RequestParam double pricePerNight, @RequestParam double totalPrice,
			@RequestParam String confirmed, @RequestParam double advancedPayment, @RequestParam String advPayCurrency) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		ReservationStatus status = ReservationStatus.reservation;
		Reservation reservation = new Reservation(reservationId, checkInDate, checkOutDate, pricePerNight, totalPrice,
				advancedPayment, advPayCurrency, status);
		reservationRepository.save(reservation);
		return "Saved";
	}
	
	@GetMapping(path = "/all-apartments")
	public @ResponseBody Iterable<Apartment> getAllApartments() {
		// This returns a JSON or XML with the users
		return apartmentRepository.findAll();
	}
	
	@GetMapping(path = "/reserved-dates")
	public ModelAndView getReservedDates(HttpSession session) {
		// This returns a JSON or XML with the users
		Set<Apartment> apartments = new LinkedHashSet<>();
		Iterable<Reservation> reservationRepositoryAll = reservationRepository.findAll();
		Apartment a;
		for(Reservation reservation : reservationRepositoryAll) {
			Apartment apartment;
			if(reservation.getApartment() != null) {
				apartment = reservation.getApartment();
				if(!apartments.contains(apartment)) {
					apartments.add(apartment);
				}
				final Apartment tmp = apartment;
				Optional<Apartment> foundApartment = apartments.stream().filter(other -> other.equals(tmp)).findFirst();
				apartment = foundApartment.get();
				//for(LocalDate date : reservation.getCheckInDate().datesUntil(reservation.getCheckOutDate()).collect(Collectors.toList()))
				for(LocalDate date : getDatesBetweenUsingJava8(reservation.getCheckInDate(), reservation.getCheckOutDate()))
					apartment.getApartmentReservations().put(date, reservation);
			}
		}
		
		for (Apartment apartment : apartments) {
			for(Entry<LocalDate, Reservation> reservationDate : apartment.getApartmentReservations().entrySet())  {
				System.out.println(reservationDate.getKey() +
						" -> " + apartment.getApartmentName() + " : " +
						reservationDate.getValue().getTourists().getName());
			}
		}
		//apartments.stream().forEach(apartment -> System.out.println(apartment.toString()));
		//return reservationRepositoryAll;
		//apartment.getApartmentReservations().forEach((date, reservation) -> System.out.println(date + reservation.getTourists().getName()));
		//session.setAttribute("att", apartments);
		ModelAndView mav = new ModelAndView();
		mav.addObject("attribute", apartments);
		mav.addObject("name", "Tomislav");
		mav.addObject("app", new Apartment(2, 1, "A1", "baba", "nista", null));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse("01-01-2005", formatter);
		mav.addObject("date", localDate);
        mav.setViewName("reservations");
		return mav;
	}

	@GetMapping(path = "/all-reservations")
	public @ResponseBody Iterable<Reservation> getAllReservatiosn() {
		// This returns a JSON or XML with the users
		return reservationRepository.findAll();
	}

	@GetMapping("/reservations")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		
		for (Reservation reservation : reservationRepository.findAll()) {
			if(reservation != null)
				System.out.println(reservation.getReservationId());
				if(reservation.getTourists() != null)
					if(reservation.getTourists().getName() != null)
						System.out.println(reservation.getTourists().getName());
						System.out.println(reservation.getConfirmed());
						System.out.println(reservation.getApartment());
		}
		
		/*
		reservationRepository.findById(Long.parseLong("1")).ifPresent(a -> System.out.println(a.getPricePerNight()));*/
		
		model.addAttribute("name", name);
		return "reservations";
	}
	
	@GetMapping("/apartments")
	public String apart(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		for (Apartment apartment : apartmentRepository.findAll()) {
			if(apartment != null)
				System.out.println(apartment.getApartmentName());

		}
		model.addAttribute("name", name);
		return "reservations";
	}
	
	public static List<LocalDate> getDatesBetweenUsingJava8(
			  LocalDate startDate, LocalDate endDate) { 
			  
			    long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate); 
			    return IntStream.iterate(0, i -> i + 1)
			      .limit(numOfDaysBetween)
			      .mapToObj(i -> startDate.plusDays(i))
			      .collect(Collectors.toList()); 
			}

}