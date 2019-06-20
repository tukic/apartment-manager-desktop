package hr.app.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hr.app.manager.ApartmentFrame.FromToPrice;
import hr.app.util.Util;

public class SaveApartment implements Runnable {
	
	ApartmentFrame apartmentFrame;
	Manager manager;
	
	public SaveApartment(ApartmentFrame apartmentFrame, Manager manager) {
		this.apartmentFrame = apartmentFrame;
		this.manager = manager;
	}

	@Override
	public void run() {
		
		String name = Util.prepareString
				(apartmentFrame.getApartmentNameTxtFld().getText().toString());
		String code = Util.prepareString
				(apartmentFrame.getApartmentCodeTxtFld().getText().toString());
		String baseCapacity = Util.prepareString
				(apartmentFrame.getApartmentCapacityTxtFld().getText().toString());
		String additionalCapacity = Util.prepareString
				(apartmentFrame.getApartmentAdditionalCapacityTxtFld().getText().toString());
		String notes = Util.prepareString
				(apartmentFrame.getNotesTxtArea().getText().toString());
		
		
		
		
		Connection conn = null;
		try {
			conn = C3poDataSource.getConnection();
			Statement stmt = conn.createStatement();
			
			String saveApartmentStr = "INSERT INTO apartment (apartmentName, internalName"
					+ ", baseCapacity, additionalCapacity, apartmentNote) "
					+ "VALUES (" + name + ", " + code + ", " + baseCapacity + ", "
							+ additionalCapacity + ", " + notes + ");";
			
			System.out.println("The SQL query is: " + saveApartmentStr); // Echo For debugging
			System.out.println();
			
			stmt.executeUpdate(saveApartmentStr, Statement.RETURN_GENERATED_KEYS);
			
			/* get apartment id (auto incremented) */

			ResultSet apartmentIdRset = stmt.getGeneratedKeys();
			String apartmentIdStr = "";
			if(apartmentIdRset.next()) {
				apartmentIdStr = Integer.toString(apartmentIdRset.getInt(1));
			}
			
						
			for(FromToPrice ftp : apartmentFrame.getFromToList()) {
				String from = Util.prepareString(ftp.from.getDateStringOrEmptyString());
				String to = Util.prepareString(ftp.to.getDateStringOrEmptyString());
				String price = Util.prepareString(ftp.price.getText());
				
				String savePricePerPeriodStr = "INSERT INTO pricePerNight (apartmentId, priceFrom,"
						+ " priceTo, price) VALUES(" + apartmentIdStr + ", " + from + ", "
								+ to +  ", " + price + ");";
				stmt.executeUpdate(savePricePerPeriodStr);
			}
			
			manager.removeAllData();
			Thread initDataThr = new Thread(new InitData(manager));
			initDataThr.start();
			initDataThr.join();
			
			new MonthCmbBoxChanged(manager);
			SwingUtilities.invokeLater(() ->
			new RefreshContentPanel(manager).refresh());
			apartmentFrame.getSaveBtn().setEnabled(true);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(apartmentFrame.getFrame(),
					Util.standardErrorBody(), Util.standardErrorTitle(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
