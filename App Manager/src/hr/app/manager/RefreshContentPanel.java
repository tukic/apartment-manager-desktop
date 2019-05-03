package hr.app.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.app.enumerations.ReservationStatus;
import hr.app.model.Apartment;
import hr.app.model.Reservation;
import hr.app.model.Tourists;

public class RefreshContentPanel {

	private Manager manager;

	public RefreshContentPanel(Manager manager) {
		this.manager = manager;
	}

	public void refresh() {
		manager.getMonthCmbBox().setPopupVisible(false);
		JPanel contentPanel = manager.getContentPanel();
		contentPanel.removeAll();
		
		manager.getTopPanel().add(manager.getNewReservationBtn());
		manager.getCentralPanel().add(manager.getMonthCmbBox(), BorderLayout.PAGE_START);

		String monthString = (String) manager.getMonthCmbBox().getSelectedItem();
		int month = 7;
		switch (monthString) {
		case "Lipanj":
			month = 6;
			break;

		case "Srpanj":
			month = 7;
			break;

		case "Kolovoz":
			month = 8;
			break;

		case "Rujan":
			month = 9;
			break;
		default:
			break;
		}

		System.out.println(manager.getMonthCmbBox().isPopupVisible());

		for (Apartment apartment : manager.getApartmentList()) {

			System.out.println(apartment.getName());

			JPanel apartmentPanel = new JPanel(new BorderLayout());
			JLabel apartmenNameLbl = new JLabel(apartment.getName(), JLabel.CENTER);
			apartmenNameLbl.setFont(new Font("Courier New", Font.BOLD, 35));
			apartmentPanel.add(apartmenNameLbl, BorderLayout.PAGE_START);

			JPanel calendarPanel = new JPanel(new GridLayout(0, 2));
			//JPanel calendarPanel = new JPanel(new GridLayout(0, 3));

			GregorianCalendar calendar = new GregorianCalendar();

			for (int i = 1; i <= YearMonth.of(manager.getYear(), month).lengthOfMonth(); i++) {

				LocalDate date = LocalDate.of(manager.getYear(), month, i);
				String status = "slobodno";
				Reservation reservation = new Reservation(-1, date, date, 0, 0, 0, ReservationStatus.CANCELLED, null);
				if (apartment.getReservedDatesMap().get(date) != null) {
					reservation = apartment.getReservedDatesMap().get(date);
					status = apartment.getReservedDatesMap().get(date).getStatus().toString();
				}

				Tourists tourists;
				Color color; //bottun color
				boolean enabled = true; //bottun enabled
				if (reservation.getTourists() != null) {
					tourists = reservation.getTourists();
					color = Color.RED;	//reserved color					
				} else {
					tourists = new Tourists(-1, "prazno");
					color = Color.GREEN; //free color
					enabled = false; //disable button
				}
				JLabel numberLbl= new JLabel(Integer.toString(i) + "   ", JLabel.RIGHT);
				//numberLbl.setBackground(color);
				//numberLbl.setSize(7, 5);
				//numberLbl.setOpaque(true);
				calendarPanel.add(numberLbl);
				JButton touristsNameBtn = new JButton(tourists.getName());
				touristsNameBtn.setEnabled(enabled);
				touristsNameBtn.setBackground(color);
				if (enabled == true) {
					int reservationId = reservation.getId();
					LocalDate checkInDate = reservation.getCheckInDate();
					LocalDate checkOutDate = reservation.getCheckOutDate();
					double pricePerNight = reservation.getPricePerNight();
					double totalPrice = reservation.getTotalPrice();
					int touristsId = tourists.getId();
					String numberOfPersons = Integer.toString(reservation.getTourists().getNumberOfPersons());
					String numberOfAdults = Integer.toString(reservation.getTourists().getNumberOfAdults());
					String numberOfChildren = Integer.toString(reservation.getTourists().getNumberOfChildren());
					String city = reservation.getTourists().getCity();
					String country = reservation.getTourists().getCountry();
					String email = reservation.getTourists().getEmail();
					String phoneNumber = reservation.getTourists().getPhoneNumber();
					boolean pets = reservation.getTourists().isPets();
					boolean advancePaid = reservation.getStatus().equals(ReservationStatus.ADVANCE_PAID);
					double advancedPayment = reservation.getAdvancedPayment();
					String touristsNote = reservation.getTourists().getTouristsNote();
					touristsNameBtn.addActionListener(l -> {
						new ReservationFrame(manager, reservationId, tourists.getName(), checkInDate, checkOutDate, pricePerNight,
								totalPrice, touristsId, numberOfPersons, numberOfAdults, numberOfChildren, city, country, email,
								phoneNumber, pets, advancePaid, advancedPayment, touristsNote, apartment.getName());
					});
				}
				calendarPanel.add(touristsNameBtn);
				//calendarPanel.add(new JLabel(status));
			}

			apartmentPanel.add(calendarPanel, BorderLayout.CENTER);
			contentPanel.add(apartmentPanel);

		}

		//manager.setContentPanel(contentPanel);
		manager.getCentralPanel().setVisible(false);
		manager.getCentralPanel().setVisible(true);

	}

}
