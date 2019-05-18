package hr.app.manager;

import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hr.app.util.Util;

public class DeleteReservation implements Runnable {

	ReservationFrame reservationFrame;
	Manager manager;
	int reservationId;
	int touristsId;

	public DeleteReservation(ReservationFrame reservationFrame, 
			Manager manager, int reservationId, int touristsId) {
		this.reservationFrame = reservationFrame;
		this.manager = manager;
		this.reservationId = reservationId;
		this.touristsId = touristsId;
	}

	@Override
	public void run() {

		Connection conn = null;
		try {			
			
			conn = C3poDataSource.getConnection();
			Statement stmt = conn.createStatement();
			
			String strDeleteReservation = "DELETE FROM reservation "
					+ "WHERE reservationId = " + reservationId + ";";
			System.out.println(strDeleteReservation);
			stmt.executeUpdate(strDeleteReservation);
			
			String strDeleteTourists = "DELETE FROM tourists "
					+ "WHERE touristsId = " + touristsId + ";";
			System.out.println(strDeleteTourists);
			stmt.executeUpdate(strDeleteTourists);
			conn.close();
			
			manager.removeAllData();
			Thread initDataThr = new Thread(new InitData(manager));
			initDataThr.start();
			initDataThr.join();
			
			new MonthCmbBoxChanged(manager);
			SwingUtilities.invokeLater(() ->
			new RefreshContentPanel(manager).refresh());
			
			reservationFrame.getDeleteBtn().setEnabled(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(reservationFrame.getFrame(),
					"Rezervacija nije izbrisana", Util.standardErrorTitle(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
