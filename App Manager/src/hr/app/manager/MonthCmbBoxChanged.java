package hr.app.manager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.app.enumerations.ReservationStatus;
import hr.app.model.Apartment;
import hr.app.model.Reservation;
import hr.app.model.Tourists;

public class MonthCmbBoxChanged implements ActionListener {

	Manager manager;

	public MonthCmbBoxChanged(Manager manager) {
		this.manager = manager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println(Thread.currentThread().getName());
		
		new RefreshContentPanel(manager).refresh();
		

	}

}
