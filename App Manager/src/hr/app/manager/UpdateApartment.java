package hr.app.manager;

import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hr.app.util.Util;

public class UpdateApartment implements Runnable {
	
	ApartmentFrame apartmentFrame;
	Manager manager;
	int apartmentId;
	
	public UpdateApartment(ApartmentFrame apartmentFrame, Manager manager, int apartmentId) {
		this.apartmentFrame = apartmentFrame;
		this.manager = manager;
		this.apartmentId = apartmentId;
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
			
			String strUpdateApartment = "UPDATE apartment "
					+ "SET apartmentName = " + name + ", internalName = " + code + ", baseCapacity = " 
					+ baseCapacity + ", additionalCapacity = " + additionalCapacity 
					+ ", apartmentNote = " + notes
					+ " WHERE apartmentId = " + apartmentId;
			
			System.out.println("The SQL query is: " + strUpdateApartment); // Echo For debugging
			System.out.println();
			
			stmt.executeUpdate(strUpdateApartment);
			conn.close();
			
			manager.removeAllData();
			Thread initDataThr = new Thread(new InitData(manager));
			initDataThr.start();
			initDataThr.join();
			
			new MonthCmbBoxChanged(manager);
			SwingUtilities.invokeLater(() -> {
				new RefreshContentPanel(manager).refresh();
				apartmentFrame.refreshApartments(manager);
				apartmentFrame.getSaveBtn().setEnabled(true);
			});
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(apartmentFrame.getFrame(),
					Util.standardErrorBody(), Util.standardErrorTitle(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
