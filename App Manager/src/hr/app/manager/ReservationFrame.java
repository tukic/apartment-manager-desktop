package hr.app.manager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Currency;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.github.lgooddatepicker.components.DatePicker;

import hr.app.model.Apartment;





public class ReservationFrame {

	int reservationId;
	int touristsId;
	private boolean update;
	private boolean save;
	private Manager manager;
	private JFrame frame;
	private JTextField guestNameTxtFld;
	private JFormattedTextField pricePerNightTxtFld;
	private JFormattedTextField advancedPaymentTxtFld;
	private DatePicker checkInDatePicker;
	private DatePicker checkOutDatePicker;
	private JButton saveBtn;
	private JFormattedTextField totalPriceTxtFld;
	private JTextField numberOfChildrenTxtFld;
	private JTextField numberOfAdultsTxtFld;
	private JTextField numberOfPersonsTxtFld;
	private JTextField cityTxtFld;
	private JTextField countryTxtFld;
	private JTextField emailTxtFld;
	private JTextField phoneNumberTxtFld;
	private JComboBox<String> apartmentComboBox;
	private JCheckBox animalsCheckBox;
	private JCheckBox advancedPaymentCheckBox;
	private JTextArea notesTxtArea;
	private DecimalFormat paymentFormat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservationFrame window = new ReservationFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ReservationFrame() {
		initialize();
		frame.setVisible(true);
	}
	
	/**
	 * Create the application.
	 */
	public ReservationFrame(Manager manager) {
		this.manager = manager;
		update = false;
		initialize();
		frame.setVisible(true);
	}
	
	
	public ReservationFrame(Manager manager, int reservationId, String name, LocalDate checkInDate, LocalDate checkOutDate, 
			double pricePerNight, double totalPrice, int touristsId, String numberOfPersons, String numberOfAdults,
			String numberOfChildren, String city, String country, String email, String phoneNumber,
			boolean animals, boolean advancePaid, double advancedPayment, String notes, String apartmentName) {
		this.manager = manager;
		this.reservationId = reservationId;
		this.touristsId = touristsId;
		update = true;
		initialize();
		guestNameTxtFld.setText(name);
		checkInDatePicker.setDate(checkInDate);
		checkOutDatePicker.setDate(checkOutDate);
		pricePerNightTxtFld.setText(paymentFormat.format(pricePerNight));
		totalPriceTxtFld.setText(paymentFormat.format(totalPrice));
		numberOfAdultsTxtFld.setText(numberOfAdults);
		numberOfChildrenTxtFld.setText(numberOfChildren);
		numberOfPersonsTxtFld.setText(numberOfPersons);
		cityTxtFld.setText(city);
		countryTxtFld.setText(country);
		emailTxtFld.setText(email);
		phoneNumberTxtFld.setText(phoneNumber);
		animalsCheckBox.setSelected(animals);
		advancedPaymentCheckBox.setSelected(advancePaid);
		advancedPaymentTxtFld.setText(paymentFormat.format(advancedPayment));
		notesTxtArea.setText(notes);
		int i = 0;
		for(i = 0; i < manager.apartmentsToStringArray().length; i++) {
			if (apartmentName.equals(manager.apartmentsToStringArray()[i])) {
				break;
			}
		}
		apartmentComboBox.setSelectedIndex(i);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(50, 50, 800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//SpringLayout springLayout = new SpringLayout();
		//frame.getContentPane().setLayout(springLayout);
		
		//formated to eur currency without symbol
		paymentFormat = (DecimalFormat) NumberFormat.getCurrencyInstance();
		paymentFormat.setCurrency(Currency.getInstance("EUR"));
		String pattern = paymentFormat.toPattern();
		String newPattern = pattern.replace("\u00A4", "").trim();
		//delete trailing space
		newPattern = newPattern.substring(0, newPattern.length()-1);
		System.out.println(newPattern);
		paymentFormat = new DecimalFormat(newPattern);
		
		JPanel container = new JPanel(new BorderLayout());
		JPanel contentPanel = new JPanel(new GridLayout(0, 4, 10, 5));
		
		JLabel guestNameLbl = new JLabel("Ime gosta");
		contentPanel.add(guestNameLbl);
		guestNameTxtFld = new JTextField();
		contentPanel.add(guestNameTxtFld);
		guestNameTxtFld.setColumns(10);
		
		JLabel apartmentLbl = new JLabel("Apartman");
		contentPanel.add(apartmentLbl);	
		//contentPanel.add(new JLabel());
		apartmentComboBox = new JComboBox<>(manager.apartmentsToStringArray());
		contentPanel.add(apartmentComboBox);
		
		JLabel checkInDateLbl = new JLabel("Datum dolaska");
		contentPanel.add(checkInDateLbl);
		checkInDatePicker = new DatePicker();
		contentPanel.add(checkInDatePicker);	
		
		JLabel numberOfPersons = new JLabel("Broj osoba");
		contentPanel.add(numberOfPersons);
		numberOfPersonsTxtFld = new JTextField();
		contentPanel.add(numberOfPersonsTxtFld);
		numberOfPersonsTxtFld.setColumns(10);
		
		JLabel checkOutDateLbl = new JLabel("Datum odlaska");
		contentPanel.add(checkOutDateLbl);
		checkOutDatePicker = new DatePicker();
		contentPanel.add(checkOutDatePicker);
		
		JLabel numberOfAdultsLbl = new JLabel("Broj odraslih");
		contentPanel.add(numberOfAdultsLbl);
		numberOfAdultsTxtFld = new JTextField();
		contentPanel.add(numberOfAdultsTxtFld);
		numberOfAdultsTxtFld.setColumns(10);
		
		JLabel pricePerNightLbl = new JLabel("Cijena po no\u0107i");
		contentPanel.add(pricePerNightLbl);
		pricePerNightTxtFld = new JFormattedTextField(paymentFormat);
		contentPanel.add(pricePerNightTxtFld);
		pricePerNightTxtFld.setColumns(10);
		
		JLabel numberOfChildrenLbl = new JLabel("Broj djece");
		contentPanel.add(numberOfChildrenLbl);
		numberOfChildrenTxtFld = new JTextField();
		numberOfChildrenTxtFld.setColumns(10);
		contentPanel.add(numberOfChildrenTxtFld);
		
		advancedPaymentCheckBox = new JCheckBox("Upla\u0107ena akontacija");
		advancedPaymentCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
		contentPanel.add(advancedPaymentCheckBox);
		contentPanel.add(new JLabel());
		
		JLabel lblGrad = new JLabel("Grad");
		contentPanel.add(lblGrad);
		cityTxtFld = new JTextField();
		cityTxtFld.setColumns(10);
		contentPanel.add(cityTxtFld);
		
		JLabel advancedPaymentLbl = new JLabel("Iznos akontacije");
		contentPanel.add(advancedPaymentLbl);
		advancedPaymentTxtFld = new JFormattedTextField(paymentFormat);
		advancedPaymentTxtFld.setColumns(10);
		contentPanel.add(advancedPaymentTxtFld);
		
		JLabel countryLbl = new JLabel("Dr\u017Eava");
		contentPanel.add(countryLbl);
		countryTxtFld = new JTextField();
		contentPanel.add(countryTxtFld);
		countryTxtFld.setColumns(10);
		
		JLabel totalPriceLbl = new JLabel("Ukupna cijena");
		contentPanel.add(totalPriceLbl);
		
		totalPriceTxtFld = new JFormattedTextField(paymentFormat);
		
		checkInDatePicker.addDateChangeListener(l -> {
			if(checkOutDatePicker.getDate() != null) {
				Apartment selectedApartment = manager.getApartmentByName(apartmentComboBox.getSelectedItem().toString());
				totalPriceTxtFld.setText(Double.toString
						(selectedApartment.getTotalPriceInDateRange
								(l.getNewDate(), checkOutDatePicker.getDate())));
			}
		});
		
		checkOutDatePicker.addDateChangeListener(l -> {
			if(checkInDatePicker.getDate() != null) {
				Apartment selectedApartment = manager.getApartmentByName(apartmentComboBox.getSelectedItem().toString());
				totalPriceTxtFld.setText(Double.toString
						(selectedApartment.getTotalPriceInDateRange
								(checkInDatePicker.getDate(), l.getNewDate())));
			}
		});
		

		
		/*DecimalFormatSymbols symbols = paymentFormat.getDecimalFormatSymbols();
		symbols.setCurrencySymbol(""); // Don't use null.
		paymentFormat.setDecimalFormatSymbols(symbols);*/
		//totalPriceTxtFld = new JFormattedTextField(paymentFormat);
		
		totalPriceTxtFld.setColumns(10);
		contentPanel.add(totalPriceTxtFld);
		
		JLabel emailLbl = new JLabel("Email");
		contentPanel.add(emailLbl);
		emailTxtFld = new JTextField();
		contentPanel.add(emailTxtFld);
		emailTxtFld.setColumns(10);
		
		contentPanel.add(new JLabel());
		contentPanel.add(new JLabel());
		
		JLabel phoneNumberLbl = new JLabel("Broj telefona");
		contentPanel.add(phoneNumberLbl);
		phoneNumberTxtFld = new JTextField();
		contentPanel.add(phoneNumberTxtFld);
		phoneNumberTxtFld.setColumns(10);
		
		contentPanel.add(new JLabel());
		contentPanel.add(new JLabel());
		
		animalsCheckBox = new JCheckBox("\u017Divotinje");
		animalsCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
		contentPanel.add(animalsCheckBox);
		contentPanel.add(new JLabel());
		
		JPanel bottomPanel = new JPanel(new BorderLayout(10,10));
		
		JLabel notesLbl = new JLabel("Bilje\u0161ke");
		JScrollPane scrollPane = new JScrollPane();
		notesTxtArea = new JTextArea();
		notesTxtArea.setRows(8);
		scrollPane.setViewportView(notesTxtArea);
		
		bottomPanel.add(notesLbl, BorderLayout.PAGE_START);
		bottomPanel.add(scrollPane, BorderLayout.CENTER);
		
		if(!update) {
			saveBtn = new JButton("Spremi");
			saveBtn.addActionListener(l -> {
				saveBtn.setEnabled(false);
				Thread saveReservationThr = new Thread(new SaveReservation(this, manager));
				saveReservationThr.start();
			});
		} else {
			saveBtn = new JButton("Spremi izmjene");
			saveBtn.addActionListener(l -> {
				saveBtn.setEnabled(false);
				Thread saveReservationThr = new Thread(new UpdateReservation(this, manager, reservationId, touristsId));
				saveReservationThr.start();
			});
		}
		
		
		bottomPanel.add(saveBtn, BorderLayout.PAGE_END);
		
		container.add(contentPanel, BorderLayout.CENTER);
		container.add(bottomPanel, BorderLayout.PAGE_END);
	
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(container);
		
		frame.getContentPane().add(panel);
		frame.setLocationRelativeTo(manager.getFrame());
		
		/*JLabel guestNameLbl = new JLabel("Ime gosta");
		springLayout.putConstraint(SpringLayout.NORTH, guestNameLbl, 16, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, guestNameLbl, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(guestNameLbl);
		
		guestNameTxtFld = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, guestNameTxtFld, -3, SpringLayout.NORTH, guestNameLbl);
		springLayout.putConstraint(SpringLayout.WEST, guestNameTxtFld, 31, SpringLayout.EAST, guestNameLbl);
		frame.getContentPane().add(guestNameTxtFld);
		guestNameTxtFld.setColumns(10);
		
		JLabel checkInDateLbl = new JLabel("Datum dolaska");
		springLayout.putConstraint(SpringLayout.NORTH, checkInDateLbl, 20, SpringLayout.SOUTH, guestNameLbl);
		springLayout.putConstraint(SpringLayout.WEST, checkInDateLbl, 0, SpringLayout.WEST, guestNameLbl);
		frame.getContentPane().add(checkInDateLbl);
		
		checkInDatePicker = new DatePicker();
		springLayout.putConstraint(SpringLayout.NORTH, checkInDatePicker, -2, SpringLayout.NORTH, checkInDateLbl);
		springLayout.putConstraint(SpringLayout.WEST, checkInDatePicker, 0, SpringLayout.WEST, guestNameTxtFld);
		frame.getContentPane().add(checkInDatePicker);
		
		JLabel checkOutDateLbl = new JLabel("Datum odlaska");
		springLayout.putConstraint(SpringLayout.NORTH, checkOutDateLbl, 20, SpringLayout.SOUTH, checkInDateLbl);
		springLayout.putConstraint(SpringLayout.WEST, checkOutDateLbl, 0, SpringLayout.WEST, guestNameLbl);
		frame.getContentPane().add(checkOutDateLbl);
		
		checkOutdatePicker = new DatePicker();
		springLayout.putConstraint(SpringLayout.NORTH, checkOutdatePicker, -2, SpringLayout.NORTH, checkOutDateLbl);
		springLayout.putConstraint(SpringLayout.WEST, checkOutdatePicker, 0, SpringLayout.WEST, guestNameTxtFld);
		frame.getContentPane().add(checkOutdatePicker);
		
		JLabel pricePerNightLbl = new JLabel("Cijena po no\u0107i");
		springLayout.putConstraint(SpringLayout.EAST, pricePerNightLbl, 0, SpringLayout.EAST, checkInDateLbl);
		frame.getContentPane().add(pricePerNightLbl);
		
		pricePerNightTxtFld = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, pricePerNightTxtFld, 9, SpringLayout.SOUTH, checkOutdatePicker);
		springLayout.putConstraint(SpringLayout.NORTH, pricePerNightLbl, 3, SpringLayout.NORTH, pricePerNightTxtFld);
		springLayout.putConstraint(SpringLayout.EAST, pricePerNightTxtFld, 0, SpringLayout.EAST, guestNameTxtFld);
		frame.getContentPane().add(pricePerNightTxtFld);
		pricePerNightTxtFld.setColumns(10);
		
		advancedPaymentCheckBox = new JCheckBox("Upla\u0107ena akontacija");
		advancedPaymentCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
		springLayout.putConstraint(SpringLayout.WEST, advancedPaymentCheckBox, 0, SpringLayout.WEST, guestNameLbl);
		frame.getContentPane().add(advancedPaymentCheckBox);
		
		JLabel advancedPaymentLbl = new JLabel("Iznos akontacije");
		springLayout.putConstraint(SpringLayout.NORTH, advancedPaymentLbl, 208, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, advancedPaymentCheckBox, -6, SpringLayout.NORTH, advancedPaymentLbl);
		springLayout.putConstraint(SpringLayout.WEST, advancedPaymentLbl, 0, SpringLayout.WEST, guestNameLbl);
		frame.getContentPane().add(advancedPaymentLbl);
		
		advancedPaymentTxtFld = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, advancedPaymentTxtFld, 205, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, advancedPaymentTxtFld, 0, SpringLayout.EAST, guestNameTxtFld);
		frame.getContentPane().add(advancedPaymentTxtFld);
		advancedPaymentTxtFld.setColumns(10);
		
		saveBtn = new JButton("Spremi");
		springLayout.putConstraint(SpringLayout.WEST, saveBtn, 0, SpringLayout.WEST, guestNameLbl);
		springLayout.putConstraint(SpringLayout.SOUTH, saveBtn, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(saveBtn);
		
		JLabel totalPriceLbl = new JLabel("Ukupna cijena");
		springLayout.putConstraint(SpringLayout.NORTH, totalPriceLbl, 16, SpringLayout.SOUTH, pricePerNightLbl);
		springLayout.putConstraint(SpringLayout.EAST, totalPriceLbl, 0, SpringLayout.EAST, checkInDateLbl);
		frame.getContentPane().add(totalPriceLbl);
		
		totalPriceTxtFld = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, totalPriceTxtFld, 0, SpringLayout.NORTH, totalPriceLbl);
		springLayout.putConstraint(SpringLayout.EAST, totalPriceTxtFld, 0, SpringLayout.EAST, guestNameTxtFld);
		frame.getContentPane().add(totalPriceTxtFld);
		totalPriceTxtFld.setColumns(10);
		
		JLabel apartmentLbl = new JLabel("Apartman");
		springLayout.putConstraint(SpringLayout.NORTH, apartmentLbl, 0, SpringLayout.NORTH, guestNameLbl);
		springLayout.putConstraint(SpringLayout.WEST, apartmentLbl, 60, SpringLayout.EAST, guestNameTxtFld);
		frame.getContentPane().add(apartmentLbl);
		
		apartmentComboBox = new JComboBox<>(manager.apartmentsToStringArray());
		springLayout.putConstraint(SpringLayout.NORTH, apartmentComboBox, -4, SpringLayout.NORTH, guestNameLbl);
		springLayout.putConstraint(SpringLayout.WEST, apartmentComboBox, 30, SpringLayout.EAST, apartmentLbl);
		springLayout.putConstraint(SpringLayout.EAST, apartmentComboBox, 104, SpringLayout.EAST, apartmentLbl);
		frame.getContentPane().add(apartmentComboBox);
		
		JLabel numberOfAdultsLbl = new JLabel("Broj odraslih");
		springLayout.putConstraint(SpringLayout.NORTH, numberOfAdultsLbl, 0, SpringLayout.NORTH, checkInDateLbl);
		springLayout.putConstraint(SpringLayout.WEST, numberOfAdultsLbl, 0, SpringLayout.WEST, apartmentLbl);
		frame.getContentPane().add(numberOfAdultsLbl);
		
		JLabel numberOfChildrenLbl = new JLabel("Broj djece");
		springLayout.putConstraint(SpringLayout.NORTH, numberOfChildrenLbl, 0, SpringLayout.NORTH, checkOutDateLbl);
		springLayout.putConstraint(SpringLayout.EAST, numberOfChildrenLbl, 0, SpringLayout.EAST, apartmentLbl);
		frame.getContentPane().add(numberOfChildrenLbl);
		
		numberOfChildrenTxtFld = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, numberOfChildrenTxtFld, -3, SpringLayout.NORTH, checkOutDateLbl);
		frame.getContentPane().add(numberOfChildrenTxtFld);
		numberOfChildrenTxtFld.setColumns(10);
		
		numberOfAdultsTxtFld = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, numberOfChildrenTxtFld, 0, SpringLayout.EAST, numberOfAdultsTxtFld);
		springLayout.putConstraint(SpringLayout.WEST, numberOfAdultsTxtFld, 0, SpringLayout.WEST, apartmentComboBox);
		springLayout.putConstraint(SpringLayout.SOUTH, numberOfAdultsTxtFld, 0, SpringLayout.SOUTH, checkInDateLbl);
		frame.getContentPane().add(numberOfAdultsTxtFld);
		numberOfAdultsTxtFld.setColumns(10);
		
		JLabel numberOfPersons = new JLabel("Broj osoba");
		springLayout.putConstraint(SpringLayout.NORTH, numberOfPersons, 0, SpringLayout.NORTH, pricePerNightLbl);
		springLayout.putConstraint(SpringLayout.WEST, numberOfPersons, 62, SpringLayout.EAST, pricePerNightTxtFld);
		frame.getContentPane().add(numberOfPersons);
		
		numberOfPersonsTxtFld = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, numberOfPersonsTxtFld, -3, SpringLayout.NORTH, pricePerNightLbl);
		springLayout.putConstraint(SpringLayout.WEST, numberOfPersonsTxtFld, 0, SpringLayout.WEST, apartmentComboBox);
		frame.getContentPane().add(numberOfPersonsTxtFld);
		numberOfPersonsTxtFld.setColumns(10);
		
		JLabel lblGrad = new JLabel("Grad");
		springLayout.putConstraint(SpringLayout.WEST, lblGrad, 0, SpringLayout.WEST, apartmentLbl);
		springLayout.putConstraint(SpringLayout.SOUTH, lblGrad, 0, SpringLayout.SOUTH, totalPriceLbl);
		frame.getContentPane().add(lblGrad);
		
		cityTxtFld = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, cityTxtFld, 0, SpringLayout.NORTH, totalPriceLbl);
		springLayout.putConstraint(SpringLayout.EAST, cityTxtFld, 0, SpringLayout.EAST, numberOfChildrenTxtFld);
		frame.getContentPane().add(cityTxtFld);
		cityTxtFld.setColumns(10);
		
		JLabel countryLbl = new JLabel("Dr\u017Eava");
		springLayout.putConstraint(SpringLayout.NORTH, countryLbl, 0, SpringLayout.NORTH, advancedPaymentCheckBox);
		springLayout.putConstraint(SpringLayout.WEST, countryLbl, 0, SpringLayout.WEST, apartmentLbl);
		frame.getContentPane().add(countryLbl);
		
		countryTxtFld = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, countryTxtFld, 1, SpringLayout.NORTH, advancedPaymentCheckBox);
		springLayout.putConstraint(SpringLayout.WEST, countryTxtFld, 0, SpringLayout.WEST, apartmentComboBox);
		frame.getContentPane().add(countryTxtFld);
		countryTxtFld.setColumns(10);
		
		JLabel emailLbl = new JLabel("Email");
		springLayout.putConstraint(SpringLayout.NORTH, emailLbl, 0, SpringLayout.NORTH, advancedPaymentLbl);
		springLayout.putConstraint(SpringLayout.WEST, emailLbl, 0, SpringLayout.WEST, apartmentLbl);
		frame.getContentPane().add(emailLbl);
		
		emailTxtFld = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, emailTxtFld, 0, SpringLayout.SOUTH, advancedPaymentLbl);
		springLayout.putConstraint(SpringLayout.EAST, emailTxtFld, 0, SpringLayout.EAST, numberOfChildrenTxtFld);
		frame.getContentPane().add(emailTxtFld);
		emailTxtFld.setColumns(10);
		
		JLabel phoneNumberLbl = new JLabel("Broj telefona");
		springLayout.putConstraint(SpringLayout.EAST, phoneNumberLbl, 0, SpringLayout.EAST, numberOfAdultsLbl);
		frame.getContentPane().add(phoneNumberLbl);
		
		phoneNumberTxtFld = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, phoneNumberLbl, 3, SpringLayout.NORTH, phoneNumberTxtFld);
		springLayout.putConstraint(SpringLayout.NORTH, phoneNumberTxtFld, 8, SpringLayout.SOUTH, emailTxtFld);
		springLayout.putConstraint(SpringLayout.WEST, phoneNumberTxtFld, 0, SpringLayout.WEST, apartmentComboBox);
		frame.getContentPane().add(phoneNumberTxtFld);
		phoneNumberTxtFld.setColumns(10);
		
		animalsCheckBox = new JCheckBox("\u017Divotinje");
		springLayout.putConstraint(SpringLayout.WEST, animalsCheckBox, 232, SpringLayout.WEST, frame.getContentPane());
		animalsCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
		springLayout.putConstraint(SpringLayout.NORTH, animalsCheckBox, 17, SpringLayout.SOUTH, phoneNumberTxtFld);
		frame.getContentPane().add(animalsCheckBox);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, animalsCheckBox);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, guestNameLbl);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -15, SpringLayout.NORTH, saveBtn);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 420, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(scrollPane);
		
		notesTxtArea = new JTextArea();
		scrollPane.setViewportView(notesTxtArea);
		
		JLabel notesLbl = new JLabel("Bilje\u0161ke");
		springLayout.putConstraint(SpringLayout.SOUTH, notesLbl, 0, SpringLayout.SOUTH, animalsCheckBox);
		springLayout.putConstraint(SpringLayout.EAST, notesLbl, 0, SpringLayout.EAST, guestNameLbl);
		frame.getContentPane().add(notesLbl);
		
		saveBtn.addActionListener(l -> {
			saveBtn.setEnabled(false);
			Thread saveReservationThr = new Thread(new SaveReservation(this, manager));
			saveReservationThr.start();
		});*/
		
	}
	
	public JTextField getGuestNameTxtFld() {
		return guestNameTxtFld;
	}

	public JTextField getPricePerNightTxtFld() {
		return pricePerNightTxtFld;
	}

	public JTextField getAdvancedPaymentTxtFld() {
		return advancedPaymentTxtFld;
	}
	
	public DatePicker getCheckInDatePicker() {
		return checkInDatePicker;
	}
	
	public DatePicker getCheckOutDatePicker() {
		return checkOutDatePicker;
	}

	public JTextField getTotalPriceTxtFld() {
		return totalPriceTxtFld;
	}

	public JTextField getNumberOfChildrenTxtFld() {
		return numberOfChildrenTxtFld;
	}

	public JTextField getNumberOfAdultsTxtFld() {
		return numberOfAdultsTxtFld;
	}

	public JTextField getNumberOfPersonsTxtFld() {
		return numberOfPersonsTxtFld;
	}

	public JTextField getCityTxtFld() {
		return cityTxtFld;
	}

	public JTextField getCountryTxtFld() {
		return countryTxtFld;
	}

	public JTextField getEmailTxtFld() {
		return emailTxtFld;
	}

	public JTextField getPhoneNumberTxtFld() {
		return phoneNumberTxtFld;
	}

	public JComboBox<String> getApartmentComboBox() {
		return apartmentComboBox;
	}

	public JCheckBox getAnimalsCheckBox() {
		return animalsCheckBox;
	}

	public JCheckBox getAdvancedPaymentCheckBox() {
		return advancedPaymentCheckBox;
	}
	
	public JTextArea getNotesTxtArea() {
		return notesTxtArea;
	}

	public JButton getSaveBtn() {
		return saveBtn;
	}
	
	public DecimalFormat getPaymentFormat() {
		return paymentFormat;
	}
}
