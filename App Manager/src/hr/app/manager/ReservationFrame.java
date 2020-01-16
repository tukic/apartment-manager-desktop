package hr.app.manager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.lgooddatepicker.components.DatePicker;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import hr.app.util.Util;


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
	private JButton makeCalculationBtn;
	private JFormattedTextField totalPriceTxtFld;
	private JTextField numberOfChildrenTxtFld;
	private JTextField numberOfAdultsTxtFld;
	private JTextField numberOfPersonsTxtFld;
	private JTextField cityTxtFld;
	private JTextField countryTxtFld;
	private JTextField emailTxtFld;
	private JTextField phoneNumberTxtFld;
	private JComboBox<String> apartmentComboBox;
	private JComboBox<String> advPayCurrencyComboBox;
	private JCheckBox animalsCheckBox;
	private JCheckBox advancedPaymentCheckBox;
	private JTextArea notesTxtArea;
	private DecimalFormat paymentFormat;
	private JPanel container;
	private JButton deleteBtn;

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
			boolean animals, boolean advancePaid, double advancedPayment, String advPayCurrency, String notes, String apartmentName) {
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
		advancedPaymentCheckBox.setSelected(Double.compare(advancedPayment, 0.f) != 0);
		advPayCurrencyComboBox.setSelectedItem(advPayCurrency);
		if(advancedPaymentCheckBox.isSelected()) {
			advancedPaymentTxtFld.setEditable(true);
		} else {
			advancedPaymentTxtFld.setEditable(false);
		}
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
				
		paymentFormat = (DecimalFormat) NumberFormat.getNumberInstance();
		paymentFormat.applyPattern("###,##0.00");
		
		container = new JPanel(new BorderLayout());
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
		
		JPanel advPayCurrencyPnl = new JPanel();
		
		advPayCurrencyPnl.add(new JLabel("Valuta akontacije"));
		advPayCurrencyComboBox = new JComboBox<>(Util.advPayCurrencyStringArray());
		
		advPayCurrencyPnl.add(advPayCurrencyComboBox);
		contentPanel.add(advPayCurrencyPnl);
		
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
		if(advancedPaymentCheckBox.isSelected()) {
			advancedPaymentTxtFld.setEditable(true);
		} else {
			advancedPaymentTxtFld.setEditable(false);
		}
		
		JLabel countryLbl = new JLabel("Dr\u017Eava");
		contentPanel.add(countryLbl);
		countryTxtFld = new JTextField();
		contentPanel.add(countryTxtFld);
		countryTxtFld.setColumns(10);
		
		JLabel totalPriceLbl = new JLabel("Ukupna cijena");
		contentPanel.add(totalPriceLbl);
		
		totalPriceTxtFld = new JFormattedTextField(paymentFormat);
		
		/*checkInDatePicker.addDateChangeListener(l -> {
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
		
		advancedPaymentCheckBox.addActionListener(l -> {
			if(advancedPaymentCheckBox.isSelected()) {
				advancedPaymentTxtFld.setEditable(true);
			}
			else {
				advancedPaymentTxtFld.setEditable(false);
			}
		});
		
		JPanel bottomButtonsPanel = new JPanel();
		if(!update) {
			saveBtn = new JButton("Spremi");
			saveBtn.addActionListener(l -> {
				saveBtn.setEnabled(false);
				if(checkInDatePicker.getDate().isAfter(checkOutDatePicker.getDate())) {
					JOptionPane.showMessageDialog(frame, "Datum odlaska mora biti nakon datuma dolaska!!!", "Gre�ka", JOptionPane.ERROR_MESSAGE);
					saveBtn.setEnabled(true);	
				}
				else {
					boolean overbooking = false;
					for(LocalDate date : checkInDatePicker.getDate().datesUntil(checkOutDatePicker.getDate()).collect(Collectors.toList())) {
						if(manager.getApartmentByName(apartmentComboBox.getSelectedItem().toString()).isDateReserved(date)) {
							overbooking = true;
							JOptionPane.showMessageDialog(frame, "Ve� postoji rezervacija u zadanom periodu!!!", "Gre�ka", JOptionPane.ERROR_MESSAGE);
							saveBtn.setEnabled(true);
							break;
						}
					}
					
					if(!overbooking) {
						Thread saveReservationThr = new Thread(new SaveReservation(this, manager));
						saveReservationThr.start();
					}
				}
			});
			bottomButtonsPanel.add(saveBtn);
		} else {
			makeCalculationBtn = new JButton("Napravi kalkulaciju");
			makeCalculationBtn.addActionListener(l -> {
				try {
					double pricePerNight = paymentFormat.parse(pricePerNightTxtFld.getText()).doubleValue();
					double totalPrice = paymentFormat.parse(totalPriceTxtFld.getText()).doubleValue();
					double advancedPayment = paymentFormat.parse(advancedPaymentTxtFld.getText()).doubleValue();
					String advancedPaymentCurrency = advPayCurrencyComboBox.getSelectedItem().toString();
					int numOfDays = checkInDatePicker.getDate().until(checkOutDatePicker.getDate()).getDays();
					new CalculationFrame(frame, numOfDays, totalPrice,
							pricePerNight, advancedPayment, advancedPaymentCurrency);
				} catch(ParseException e) {
					e.printStackTrace();
				}
			});
		


			bottomButtonsPanel.add(makeCalculationBtn);
			
			saveBtn = new JButton("Spremi izmjene");
			saveBtn.addActionListener(l -> {
				saveBtn.setEnabled(false);
				if(checkInDatePicker.getDate().isAfter(checkOutDatePicker.getDate())) {
					JOptionPane.showMessageDialog(frame, "Datum odlaska mora biti nakon datuma dolaska!!!", "Greška", JOptionPane.ERROR_MESSAGE);
					saveBtn.setEnabled(true);	
				}
				else {
					Thread saveReservationThr = new Thread(new UpdateReservation
						(this, manager, reservationId, touristsId, 
						manager.getApartmentByName
						(apartmentComboBox.getSelectedItem().toString()).getId()));
					saveReservationThr.start();
				}
			});
			bottomButtonsPanel.add(saveBtn);
			deleteBtn = new JButton("Obriši rezervaciju");
			deleteBtn.addActionListener(l -> {
				deleteBtn.setEnabled(false);
				int confirmedDeleting = JOptionPane.showConfirmDialog(frame, "Jeste li sigurni da �elite obrisati rezervaciju?",
						"Potvrda brisanja", JOptionPane.YES_NO_OPTION, JOptionPane.CANCEL_OPTION);
				if(confirmedDeleting == 0) {
					Thread deleteReservationThr = new Thread(new DeleteReservation(this, manager, reservationId, touristsId));
					deleteReservationThr.start();
				}
				else {
					deleteBtn.setEnabled(true);
				}
			});
			bottomButtonsPanel.add(deleteBtn);
		}
		
		checkInDatePicker.addDateChangeListener(l -> calculateTotalPrice());
		checkOutDatePicker.addDateChangeListener(l -> calculateTotalPrice());
		
		pricePerNightTxtFld.getDocument().addDocumentListener(
				new DocumentListener() {
					
					@Override
					public void removeUpdate(DocumentEvent e) {
						calculateTotalPrice();
					}
					
					@Override
					public void insertUpdate(DocumentEvent e) {
						calculateTotalPrice();
					}
					
					@Override
					public void changedUpdate(DocumentEvent e) {
						calculateTotalPrice();	
					}
							
		});
		
		bottomPanel.add(bottomButtonsPanel, BorderLayout.PAGE_END);
		
		container.add(contentPanel, BorderLayout.CENTER);
		container.add(bottomPanel, BorderLayout.PAGE_END);
	
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(container);
		
		frame.getContentPane().add(panel);
		frame.setLocationRelativeTo(manager.getFrame());
		
		
	}

	//TODO: greska kod kod datuma neka 
	private void calculateTotalPrice() {
		if(checkInDatePicker.isTextFieldValid() && checkOutDatePicker.isTextFieldValid()) {
			try {
				int numberOfNights = checkInDatePicker.getDate().until(checkOutDatePicker.getDate()).getDays();
				double pricePerNight;
				pricePerNight = paymentFormat.parse(pricePerNightTxtFld.getText()).doubleValue();
				totalPriceTxtFld.setText(paymentFormat.format((double) pricePerNight * numberOfNights));
			} catch (Exception e) {
				totalPriceTxtFld.setText("0,00");
				e.printStackTrace();
			}
		}
	}
	
	public JPanel getContainer() {
		return container;
	}
	
	public JFrame getFrame() {
		return frame;
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
	
	public JComboBox<String> getAdvPayCurrencyComboBox() {
		return advPayCurrencyComboBox;
	}
	
	public JTextArea getNotesTxtArea() {
		return notesTxtArea;
	}

	public JButton getSaveBtn() {
		return saveBtn;
	}
	
	public JButton getDeleteBtn() {
		return deleteBtn;
	}
	
	public DecimalFormat getPaymentFormat() {
		return paymentFormat;
	}
}
