package hr.app.manager;

import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hr.app.util.Util;

public class DeleteApartment implements Runnable {
	
	ApartmentFrame apartmentFrame;
	Manager manager;
	int apartmentId;
	
	public DeleteApartment(ApartmentFrame apartmentFrame, Manager manager, int apartmentId) {
		this.apartmentFrame = apartmentFrame;
		this.manager = manager;
		this.apartmentId = apartmentId;
	}

	@Override
	public void run() {


		Connection conn = null;
		try {			
			
			conn = C3poDataSource.getConnection();
			Statement stmt = conn.createStatement();
			
			String strDeleteApartment = "DELETE FROM apartment "
					+ "WHERE apartmentId = " + apartmentId + ";";
			System.out.println(strDeleteApartment);
			stmt.executeUpdate(strDeleteApartment);
			
			conn.close();
			
			manager.removeAllData();
			Thread initDataThr = new Thread(new InitData(manager));
			initDataThr.start();
			initDataThr.join();
			
			new MonthCmbBoxChanged(manager);
			SwingUtilities.invokeLater(() -> {
				new RefreshContentPanel(manager).refresh();
				apartmentFrame.refreshApartments(manager);
				apartmentFrame.getDeleteBtn().setEnabled(true);
			});
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(apartmentFrame.getFrame(),
					"Apartman nije izbrisan", Util.standardErrorTitle(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
