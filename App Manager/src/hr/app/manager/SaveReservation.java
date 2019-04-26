package hr.app.manager;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.SwingUtilities;

import hr.app.util.Util;

public class SaveReservation implements Runnable {

	private ReservationFrame reservationFrame;
	private Manager manager;
	
	public SaveReservation(ReservationFrame reservationFrame, Manager manager) {
		this.reservationFrame = reservationFrame;
		this.manager = manager;
	}
	
	@Override
	public void run() {
		Connection conn = null;
		try {
			
			String nameStr = Util.prepareString(reservationFrame.getGuestNameTxtFld().getText());
			String checkInDateStr = Util.prepareString(reservationFrame.getCheckInDatePicker().getDateStringOrEmptyString());
			String checkOutDateStr = Util.prepareString(reservationFrame.getCheckOutDatePicker().getDateStringOrEmptyString());
			String pricePerNightStr = Util.prepareString(reservationFrame.getPricePerNightTxtFld().getText());
			String totalPriceStr = Util.prepareString(reservationFrame.getTotalPriceTxtFld().getText());
			String advancedPaymentStr = Util.prepareString(reservationFrame.getAdvancedPaymentTxtFld().getText());
			String apartmentNameStr = Util.prepareString(reservationFrame.getApartmentComboBox().getSelectedItem().toString());
			String numberOfAdultsStr = Util.prepareString(reservationFrame.getNumberOfAdultsTxtFld().getText());
			String numberOfChildrenStr = Util.prepareString(reservationFrame.getNumberOfChildrenTxtFld().getText());
			String numberOfPersonsStr = Util.prepareString(reservationFrame.getNumberOfPersonsTxtFld().getText());
			String cityStr = Util.prepareString(reservationFrame.getCityTxtFld().getText());
			String countryStr = Util.prepareString(reservationFrame.getCountryTxtFld().getText());
			String emailStr = Util.prepareString(reservationFrame.getEmailTxtFld().getText());
			String phoneNumberStr = Util.prepareString(reservationFrame.getPhoneNumberTxtFld().getText());
			String animalsStr = Util.prepareString(Boolean.toString(reservationFrame.getAnimalsCheckBox().isSelected()));
			String notesStr = Util.prepareString(reservationFrame.getNotesTxtArea().getText());
			
			//String apartmentId = manager.getApartmentById()
			
			String url = "jdbc:mysql://85.10.205.173:3306/app_ukic";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, "johto_db", "antejoni2007");
			System.out.println("Database connection established");
			
			

			Statement stmt = conn.createStatement();
			
			/* 
			 * 
			 */
			
			String strUpdateTourists = "INSERT INTO tourists (name, country, city, numberOfAdults, numberOfChildren, numberOfPersons, email, phoneNumber, pets, touristsNote)"
					+ " VALUES ('" + nameStr + "','" + countryStr + "','" + cityStr + "','" + numberOfAdultsStr + "','" + numberOfChildrenStr + "','" +  numberOfPersonsStr + "','" 
					+ emailStr + "','" + phoneNumberStr + "'," + animalsStr + ",'" + notesStr +  "');";	
			
			System.out.println("The SQL query is: " + strUpdateTourists); // Echo For debugging
			System.out.println();
			
			stmt.executeUpdate(strUpdateTourists);
			System.out.println("The SQL query is SELECT touristsId FROM tourists WHERE name LIKE '" + nameStr + "';");
			ResultSet touristsIdRset = stmt.executeQuery("SELECT touristsId FROM tourists WHERE name LIKE '" + nameStr + "';");
			touristsIdRset.next();
			String touristsIdStr = touristsIdRset.getString("touristsId");
			
			ResultSet apartmentIdRset = stmt.executeQuery("SELECT apartmentId FROM apartment WHERE apartmentName LIKE '" + apartmentNameStr + "';");
			apartmentIdRset.next();
			String apartmentIdStr = apartmentIdRset.getString("apartmentId");
			String strUpdateReservation = "INSERT INTO reservation (checkInDate, checkOutDate, pricePerNight, totalPrice, confirmed, advancedPayment, apartmentId, touristsId) VALUES ('" + 
					checkInDateStr + "','" + checkOutDateStr + "','" + pricePerNightStr + "','" + totalPriceStr 
					+ "','reservation','" + advancedPaymentStr + "','" + apartmentIdStr + "','" + touristsIdStr + "');";
			System.out.println("The SQL query is: " + strUpdateReservation );
			
			stmt.executeUpdate(strUpdateReservation);
			
			/*stmt = conn.createStatement();
			
			
			String strSelect = "select * from apartment natural join reservation natural join tourists";
			System.out.println("The SQL query is: " + strSelect); // Echo For debugging
			System.out.println();
			ResultSet rset = stmt.executeQuery(strSelect);
			
			System.out.println("The records selected are:");
			int rowCount = 0;
			while (rset.next()) { // Move the cursor to the next row, return false if no more row
				String apartmentName = rset.getString("apartmentName");
				System.out.println("apartmentName = " + apartmentName);
				int apartmentId = rset.getInt("apartmentId");
				System.out.println("apartmentId = " + apartmentId);
				int reservationId = rset.getInt("reservationId");
				System.out.println("reservationId = " + reservationId);
				LocalDate checkInDate = rset.getDate("checkInDate").toLocalDate();
				System.out.println("checkInDate = " + checkInDate);
				LocalDate checkOutDate = rset.getDate("checkOutDate").toLocalDate();
				System.out.println("checkOutDate = " + checkOutDate);
				int pricePerNight = rset.getInt("pricePerNight");
				System.out.println("pricePerNight = " + pricePerNight);
				int touristsId = rset.getInt("touristsId");
				System.out.println("touristsId = " + touristsId);
				String touristsName = rset.getString("name");
				System.out.println("touristsName = " + touristsName);
				String reservationStatusStr = rset.getString("confirmed");
				System.out.println("confirmed = " + reservationStatusStr);
				
				ReservationStatus status = ReservationStatus.RESERVATION;
				
				switch (reservationStatusStr) {
				case "inquiry": 
					status = ReservationStatus.INQUIRY;
					break;
					
				case "offer":
					status = ReservationStatus.OFFER;
					break;
					
				case "reservation":
					status = ReservationStatus.RESERVATION;
					break;
					
				case "advancePaid":
					status = ReservationStatus.ADVANCE_PAID;
					break;
					
				case "cancelled":
					status = ReservationStatus.CANCELLED;
					break;

				default:
					break;
				}
				
				if (!manager.getApartmentList().contains(new Apartment(apartmentId, apartmentName))) {
					manager.addToApartmentList(new Apartment(apartmentId, apartmentName));
				}	
				Reservation reservation = new Reservation(reservationId, checkInDate, checkOutDate, pricePerNight, status);
				
				if(touristsName != null) {
					reservation.setTourists(new Tourists(touristsId, touristsName));
				}
				
				manager.getApartmentById(apartmentId).addToReservationList(reservation);
				System.out.println("apartmentName = " + manager.getApartmentById(apartmentId).getName());
				
				// double price = rset.getDouble("price");
				// int qty = rset.getInt("qty");
				// System.out.println(title + ", " + price + ", " + qty);
				System.out.println("od");
				++rowCount;
			}
			System.out.println("Total number of records = " + rowCount);*/
			manager.removeAllData();
			
			Thread initDataThr = new Thread(new InitData(manager));
			initDataThr.start();
			initDataThr.join();
			
			MonthCmbBoxChanged refreshData = new MonthCmbBoxChanged(manager);
			SwingUtilities.invokeLater(() ->
			new RefreshContentPanel(manager).refresh());
			
			reservationFrame.getSaveBtn().setEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
