package hr.app.manager;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.SwingUtilities;

import hr.app.enumerations.ReservationStatus;
import hr.app.model.Apartment;
import hr.app.model.Reservation;
import hr.app.model.Tourists;

/**
 * 
 * Initalizes data from database
 * 
 * 
 * @author Tomislav Ukiæ
 *
 */
public class InitData implements Runnable {
	Manager manager;
	
	/**
	 * @param manager
	 */
	public InitData(Manager manager) {
		this.manager = manager;
	}

	@Override
	public void run() {
		Connection conn = null;
		try {
			String url = "jdbc:mysql://85.10.205.173:3306/app_ukic";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, "johto_db", "antejoni2007");
			System.out.println("Database connection established");
			
			

			Statement stmt = conn.createStatement();
			
			/* 
			 * 
			 */
			
			String selectApartment = "select * from apartment";
			ResultSet apartmentsRset = stmt.executeQuery(selectApartment);
			while(apartmentsRset.next()) {
				manager.addToApartmentList(new Apartment(apartmentsRset.getInt("apartmentId"), apartmentsRset.getString("apartmentName")));
			}
			
			
			stmt = conn.createStatement();
			/* 
			 * 
			 */
			
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
			System.out.println("Total number of records = " + rowCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*SwingUtilities.invokeLater(()-> {
			new RefreshContentPanel(manager).refresh();
			manager.getFrame().setVisible(false);
			manager.getFrame().setVisible(true);
		});*/
	}

}
