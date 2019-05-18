/**
 * 
 */
package hr.app.manager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;

import hr.app.model.Apartment;

/**
 * @author Tomislav Ukiæ
 *
 */
public class ApartmentFrame {
	
	public class FromToPrice {
		DatePicker from;
		DatePicker to;
		JTextField price;
		
		public FromToPrice(DatePicker from, DatePicker to, JTextField price) {
			this.from = from;
			this.to = to;
			this.price = price;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof FromToPrice) {
				FromToPrice other = (FromToPrice) obj;
				if(other.from.getText().equals(this.from.getText())
						&& other.to.getText().equals(this.to.getText())
								&& other.price.getText().equals(this.price.getText()))
					return true;
				return false;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return price.getText().hashCode() + to.getText().hashCode() 
					+ from.getText().hashCode();
		}
	}
	
	private Manager manager;
	private JFrame frame;
	private JTextField apartmentNameTxtFld;
	private JTextField apartmentCodeTxtFld;
	private JTextField apartmentCapacityTxtFld;
	private JTextField apartmentAdditionalCapacityTxtFld;
	private JTextArea notesTxtArea;
	private List<FromToPrice> fromToList;
	private JButton saveBtn;
	private boolean update;
	private int apartmentId;
	private JPanel apartmentListPanel;
	private JButton apartmentBtn;
	private JPanel splitPanel;
	private JPanel container;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApartmentFrame window = new ApartmentFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * if update open list of apartments at left
	 */
	public ApartmentFrame(Manager manager, boolean update) {
		this.manager = manager;
		this.update = update;
		initialize();
		frame.setVisible(true);
	}
	
	public ApartmentFrame() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(50, 50, 800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		container = new JPanel(new BorderLayout(10, 10));
		splitPanel = new JPanel(new BorderLayout(10, 10));
		
		JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
		JPanel informationPanel = new JPanel(new GridLayout(4, 2));
		JPanel pricePerPeriodPanel = new JPanel(new GridLayout(0, 3));
		
		JLabel apartmentNameLbl = new JLabel("Naziv");
		informationPanel.add(apartmentNameLbl);
		apartmentNameTxtFld = new JTextField();
		informationPanel.add(apartmentNameTxtFld);
		
		JLabel apartmentCodeLbl = new JLabel("Šifra");
		informationPanel.add(apartmentCodeLbl);
		apartmentCodeTxtFld = new JTextField();
		informationPanel.add(apartmentCodeTxtFld);
		
		JLabel apartmentCapacityLbl = new JLabel("Osnovni ležajevi");
		informationPanel.add(apartmentCapacityLbl);
		apartmentCapacityTxtFld = new JTextField();
		informationPanel.add(apartmentCapacityTxtFld);
		
		JLabel apartmentAdditionalCapacityLbl = new JLabel("Dodatni ležajevi");
		informationPanel.add(apartmentAdditionalCapacityLbl);
		apartmentAdditionalCapacityTxtFld = new JTextField();
		
		informationPanel.add(apartmentAdditionalCapacityTxtFld);
		
		JButton addPeriodsBtn = new JButton("Dodaj periode");

		contentPanel.add(informationPanel, BorderLayout.CENTER);
		
		JButton removePeriodsBtn = new JButton("Makni period");
				
		JPanel bottomPanel = new JPanel(new BorderLayout());
		
		bottomPanel.add(pricePerPeriodPanel, BorderLayout.PAGE_START);
		
		JPanel periodButtonsPanel = new JPanel();
		
		periodButtonsPanel.add(addPeriodsBtn);
		periodButtonsPanel.add(removePeriodsBtn);
		bottomPanel.add(periodButtonsPanel, BorderLayout.CENTER);
		
		JPanel notesPanel = new JPanel(new BorderLayout());
		notesPanel.add(new JLabel("Bilješke"), BorderLayout.PAGE_START);
		
		notesTxtArea = new JTextArea();
		notesTxtArea.setRows(8);
		JScrollPane notesScrollPane = new JScrollPane(notesTxtArea);
		notesPanel.add(notesScrollPane, BorderLayout.CENTER);
		
		if(update) {
			saveBtn = new JButton("Spremi promjene");
		}
		else {
			saveBtn = new JButton("Spremi");
		}
		notesPanel.add(saveBtn, BorderLayout.PAGE_END);
		
		bottomPanel.add(notesPanel, BorderLayout.PAGE_END);
		
		contentPanel.add(bottomPanel, BorderLayout.PAGE_END);
		
		splitPanel.add(contentPanel, BorderLayout.CENTER);
		
		if(update) {
			apartmentListPanel = new JPanel(new GridLayout(0,1));
			for(Apartment apartment : manager.getApartmentList()) {
				apartmentBtn = new JButton(apartment.getName());
				apartmentBtn.addActionListener(l -> {
					apartmentNameTxtFld.setText(apartment.getName());
					apartmentCodeTxtFld.setText(apartment.getInternalName());
					apartmentCapacityTxtFld.setText(Integer.toString(apartment.getBaseCapacity()));
					apartmentAdditionalCapacityTxtFld.setText(Integer.toString(apartment.getAdditionalCapacity()));
					notesTxtArea.setText(apartment.getApartmentNote());
					apartmentId = apartment.getId();
				});
				apartmentListPanel.add(apartmentBtn);
			}
			splitPanel.add(apartmentListPanel, BorderLayout.LINE_START);
		}		
		
		container.add(splitPanel);
		frame.add(container);
		
		fromToList = new LinkedList<>();
		
		pricePerPeriodPanel.add(new JLabel("Od"));
		pricePerPeriodPanel.add(new JLabel("Do"));
		pricePerPeriodPanel.add(new JLabel("Cijena"));
		addPricePerPeriod(pricePerPeriodPanel, fromToList);
		
		bottomPanel.add(pricePerPeriodPanel, BorderLayout.PAGE_START);
		
		addPeriodsBtn.addActionListener(l -> {
			addPricePerPeriod(pricePerPeriodPanel, fromToList);
			bottomPanel.remove(pricePerPeriodPanel);
			bottomPanel.add(pricePerPeriodPanel, BorderLayout.PAGE_START);
			frame.setVisible(true);
		});
		
		removePeriodsBtn.addActionListener(l -> {
			
			try {
				FromToPrice ftp = fromToList.get(fromToList.size()-1);
				pricePerPeriodPanel.remove(ftp.from);
				pricePerPeriodPanel.remove(ftp.to);
				pricePerPeriodPanel.remove(ftp.price);
				fromToList.remove(fromToList.size()-1);
			} catch (IndexOutOfBoundsException ex) {
				JOptionPane.showMessageDialog(frame, "Svi periodi maknuti");
			}

			frame.setVisible(true);
		});
		

		if(update) {
			saveBtn.addActionListener(l-> {
				saveBtn.setEnabled(false);
				List<FromToPrice> tmp = new LinkedList<>();
				for(FromToPrice ftp : fromToList) {
					if(ftp.from.getText().equals("") || ftp.to.getText().equals("") 
							|| ftp.price.getText().equals("")) {
						tmp.add(ftp);
					}
				}
				fromToList.removeAll(tmp);
				Thread saveApartmentThr = new Thread(new UpdateApartment(this, manager, apartmentId));
				saveApartmentThr.start();
			});
		}
		else {
			saveBtn.addActionListener(l-> {
				saveBtn.setEnabled(false);
				List<FromToPrice> tmp = new LinkedList<>();
				for(FromToPrice ftp : fromToList) {
					if(ftp.from.getText().equals("") || ftp.to.getText().equals("") 
							|| ftp.price.getText().equals("")) {
						tmp.add(ftp);
					}
				}
				fromToList.removeAll(tmp);
				Thread saveApartmentThr = new Thread(new SaveApartment(this, manager));
				saveApartmentThr.start();
				
			});
		}
		
	}

	private void addPricePerPeriod(JPanel pricePerPeriodPanel, List<FromToPrice> list) {
		DatePicker startPeriodPicker = new DatePicker();
		DatePicker endPeriodPicker = new DatePicker();
		JTextField periodPriceTxtFld = new JTextField();
		
		pricePerPeriodPanel.add(startPeriodPicker);
		pricePerPeriodPanel.add(endPeriodPicker);
		pricePerPeriodPanel.add(periodPriceTxtFld);

		list.add(new FromToPrice(startPeriodPicker, endPeriodPicker, periodPriceTxtFld));
	}
	
	public void refreshApartments(Manager manager) {
		this.manager = manager;
		splitPanel.remove(this.apartmentListPanel);
		apartmentListPanel = new JPanel(new GridLayout(0,1));
		for(Apartment apartment : manager.getApartmentList()) {
			apartmentBtn = new JButton(apartment.getName());
			apartmentBtn.addActionListener(l -> {
				apartmentNameTxtFld.setText(apartment.getName());
				apartmentCodeTxtFld.setText(apartment.getInternalName());
				apartmentCapacityTxtFld.setText(Integer.toString(apartment.getBaseCapacity()));
				apartmentAdditionalCapacityTxtFld.setText(Integer.toString(apartment.getAdditionalCapacity()));
				notesTxtArea.setText(apartment.getApartmentNote());
				apartmentId = apartment.getId();
			});
			apartmentListPanel.add(apartmentBtn);
		}
		splitPanel.add(apartmentListPanel, BorderLayout.LINE_START);
		container.add(splitPanel);
		frame.setVisible(true);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public JTextField getApartmentNameTxtFld() {
		return apartmentNameTxtFld;
	}
	
	public JTextField getApartmentCodeTxtFld() {
		return apartmentCodeTxtFld;
	}
	
	public JTextField getApartmentCapacityTxtFld() {
		return apartmentCapacityTxtFld;
	}
	
	public JTextField getApartmentAdditionalCapacityTxtFld() {
		return apartmentAdditionalCapacityTxtFld;
	}
	
	public JTextArea getNotesTxtArea() {
		return notesTxtArea;
	}
	
	public JButton getSaveBtn() {
		return saveBtn;
	}
	
	public List<FromToPrice> getFromToList() {
		return fromToList;
	}
	
}
