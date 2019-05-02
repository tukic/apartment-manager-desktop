package hr.app.manager;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.SwingUtilities;

import hr.app.util.Util;

public class UpdateReservation implements Runnable {

	private ReservationFrame reservationFrame;
	private Manager manager;
	private int reservationId;
	private int touristsId;
	
	public UpdateReservation(ReservationFrame reservationFrame, Manager manager, int reservationId, int touristsId) {
		this.reservationFrame = reservationFrame;
		this.manager = manager;
		this.reservationId = reservationId;
		this.touristsId = touristsId;
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
			String touristsIdStr = Util.prepareString(Integer.toString(touristsId));
			
			
			//String apartmentId = manager.getApartmentById()
			
			//String url = "jdbc:mysql://85.10.205.173:3306/app_ukic";
			//Class.forName("com.mysql.jdbc.Driver");
			//conn = DriverManager.getConnection(url, "johto_db", "antejoni2007");
			conn = C3poDataSource.getConnection();
			System.out.println("Database connection established");
			
			

			Statement stmt = conn.createStatement();
			
			/* 
			 * 
			 */
			
			String strUpdateTourists = "UPDATE tourists "
					+ "SET name = '" + nameStr + "', country = '" + countryStr + "', city = '" 
					+ cityStr + "', numberOfAdults = '" + numberOfAdultsStr 
					+ "', numberOfChildren = '" + numberOfChildrenStr + "', numberOfPersons = '" 
					+ numberOfPersonsStr + "', email = '" + emailStr
					+ "', phoneNumber = '" + phoneNumberStr + "', pets = " + animalsStr 
					+ ", touristsNote = '" + notesStr 
					+ "' WHERE touristsId = '" + touristsId + "'";
			
			System.out.println("The SQL query is: " + strUpdateTourists); // Echo For debugging
			System.out.println();
			
			stmt.executeUpdate(strUpdateTourists);
			//System.out.println("The SQL query is SELECT touristsId FROM tourists WHERE name LIKE '" + nameStr + "';");
			//ResultSet touristsIdRset = stmt.executeQuery("SELECT touristsId FROM tourists WHERE name LIKE '" + nameStr + "';");
			//touristsIdRset.next();
			//String touristsIdStr = touristsIdRset.getString("touristsId");
			
			ResultSet apartmentIdRset = stmt.executeQuery("SELECT apartmentId FROM apartment WHERE apartmentName LIKE '" + apartmentNameStr + "';");
			apartmentIdRset.next();
			String apartmentIdStr = apartmentIdRset.getString("apartmentId");
			String strUpdateReservation = "UPDATE reservation "
					+ "SET checkInDate = '" + checkInDateStr + "', checkOutDate = '" + checkOutDateStr 
					+ "', pricePerNight = '" + pricePerNightStr + "', totalPrice = '" + totalPriceStr 
					+ "', confirmed = 'reservation', advancedPayment = '" + advancedPaymentStr 
					+ "', apartmentId = '" + apartmentIdStr + "', touristsId = '" + touristsIdStr 
					+ "' WHERE reservationId = '" + reservationId + "'";
			System.out.println("The SQL query is: " + strUpdateReservation );
			
			
			stmt.executeUpdate(strUpdateReservation);
			
			manager.removeAllData();
			
			conn.close();
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
