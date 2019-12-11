package hr.app.manager;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingScreen {
	
	private Manager manager;

	public LoadingScreen(Manager manager) {
		this.manager = manager;
	}

	public void show() {

		JPanel contentPanel = manager.getContentPanel();
		contentPanel.removeAll();

		JLabel loadingReservationLbl = new JLabel("Učitavanje rezervacija...", JLabel.CENTER);
		loadingReservationLbl.setFont(new Font("Courier New", Font.BOLD, 35));
		contentPanel.add(loadingReservationLbl, BorderLayout.PAGE_START);
		manager.setContentPanel(contentPanel);
		manager.getCentralPanel().setVisible(false);
		manager.getCentralPanel().setVisible(true);
		manager.getFrame().setVisible(true);
	}

}
