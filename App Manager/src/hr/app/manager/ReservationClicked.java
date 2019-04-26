package hr.app.manager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.github.lgooddatepicker.components.DatePicker;

public class ReservationClicked implements ActionListener {
	
	private Manager manager;

	public ReservationClicked(Manager manager) {
		this.manager = manager;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new ReservationFrame(manager);
		
	}

}
