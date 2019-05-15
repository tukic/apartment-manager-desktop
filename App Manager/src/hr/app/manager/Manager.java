package hr.app.manager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import hr.app.model.Apartment;

/**
 * @author Tomislav Ukiæ
 * 
 * The Manager program implements an application that
 * manages booking of an apartment
 *
 */

public class Manager {
	
	private final int year = 2019;
	private final int month = 7;
	private final String[] monthComboBoxArray = {"Lipanj", "Srpanj", "Kolovoz", "Rujan"};

	private List<Apartment> apartmentList = new LinkedList<>();
	Thread initDataThr;
	private JFrame frame;
	private JComboBox<String> monthCmbBox;
	private JPanel contentPanel;
	private JPanel centralPanel;
	private JPanel topPanel;
	private JButton newReservationBtn;
	private JButton newApartmentBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager window = new Manager();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread backupDataStartThr = new Thread(new BackupData());
		backupDataStartThr.start();
	
	}

	/**
	 * Create the application.
	 */
	public Manager() {
		
		String url = "jdbc:mysql://85.10.205.173:3306/app_ukic";
		
		initDataThr = new Thread(new InitData(this));
		initDataThr.start();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1006, 634);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		topPanel = new JPanel();
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		newReservationBtn = new JButton("Nova rezervacija");
		newReservationBtn.addActionListener(new ReservationClicked(this));
		//topPanel.add(newReservationBtn);		
		
		newApartmentBtn = new JButton("Novi apartman");
		newApartmentBtn.addActionListener(l -> new ApartmentFrame(this));

		centralPanel = new JPanel();
		frame.getContentPane().add(centralPanel, BorderLayout.CENTER);
		
		centralPanel.setLayout(new BorderLayout());
		contentPanel = new JPanel();
		monthCmbBox = new JComboBox<>(monthComboBoxArray);
		monthCmbBox.addActionListener(new MonthCmbBoxChanged(this));
		

		System.out.println();
		
		/*
		for(Apartment apartment : apartmentList) {
						
			JPanel apartmentPanel = new JPanel(new BorderLayout());
			apartmentPanel.add(new JLabel(apartment.getName()), BorderLayout.PAGE_START);
					
			JPanel calendarPanel = new JPanel(new GridLayout(0, 3));
			
			for(int i = 1; i <= YearMonth.of(year, month).lengthOfMonth(); i++) {
				
				LocalDate date = LocalDate.of(year, month, i);
				String status = "slobodno";
				Reservation reservation = new Reservation(-1, date, date, 0, ReservationStatus.CANCELLED);
				//TODO date.equals pogreška
				
				if(apartment.getReservedDatesMap().get(date) != null) {
					reservation = apartment.getReservedDatesMap().get(date);
					status = apartment.getReservedDatesMap().get(date).getStatus().toString();
				}
				
				Tourists tourists;
				if(reservation.getTourists() != null) {
					tourists = reservation.getTourists();
				}
				else {
					tourists = new Tourists(-1, "prazno");
				}
				
				calendarPanel.add(new JLabel(Integer.toString(i)));
				JButton touristsNameBtn = new JButton(tourists.getName());
				touristsNameBtn.addActionListener(l -> {
					
				});
				calendarPanel.add(touristsNameBtn);
				calendarPanel.add(new JLabel(status));		
			}
			
			apartmentPanel.add(calendarPanel, BorderLayout.CENTER);
			contentPanel.add(apartmentPanel);
			
		}
		*/
		
		
		centralPanel.add(new JScrollPane(contentPanel), BorderLayout.CENTER);
		frame.add(centralPanel);
		new LoadingScreen(this).show();
		
		
		/*try {
			initDataThr.join();				//waiting initDataThr to finish so data could be taken in time
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

		//new RefreshContentPanel(this).refresh();
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				Thread backupDataFinishThr = new Thread(new BackupData());
				backupDataFinishThr.start();
		    }
		});
				
	}

	public void addToApartmentList(Apartment apartment) {
		apartmentList.add(apartment);
	}
	
	public List<Apartment> getApartmentList() {
		return apartmentList;
	}
	
	public JPanel getCentralPanel() {
		return centralPanel;
	}
	
	public void setCentralPanel(JPanel centralPanel) {
		this.centralPanel = centralPanel;
	}
	
	public JComboBox<String> getMonthCmbBox() {
		return monthCmbBox;
	}

	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public JPanel getContentPanel() {
		return contentPanel;
	}
	
	public void setContentPanel(JPanel contentPanel) {
		this.contentPanel = contentPanel;
	}
	
	public JButton getNewReservationBtn() {
		return newReservationBtn;
	}
	
	public JButton getNewApartmentBtn() {
		return newApartmentBtn;
	}
	
	public JPanel getTopPanel() {
		return topPanel;
	}
	
	
	public Apartment getApartmentFromList(Apartment apartment) {
		Apartment found = null;
		for(Apartment a : apartmentList) {
			if(apartment.equals(a)) {
				found = a;
				break;
			}
		}
		return found;
	}
	
	public Apartment getApartmentById(int id) {
		Apartment found = null;
		for(Apartment a : apartmentList) {
			found = new Apartment(id, null);
			if(found.equals(a)) {
				found = a;
				break;
			}
		}
		return found;
	}
	
	public String[] apartmentsToStringArray() {
		String[] apartmentsStringArray = new String[getApartmentList().size()];
		for(int i = 0; i < apartmentsStringArray.length; i++) {
			apartmentsStringArray[i] = getApartmentList().get(i).getName();
		}
		return apartmentsStringArray;
	}
	
	/*
	 * finds apartment by name
	 * if apartment not found return null
	 */
	public Apartment getApartmentByName(String name) {
		for(Apartment apartment : apartmentList) {
			if(apartment.getName().equals(name)) {
				return apartment;
			}
		}
		return null;
	}
	
	public void removeAllData() {
		apartmentList.clear();
	}
	
}
