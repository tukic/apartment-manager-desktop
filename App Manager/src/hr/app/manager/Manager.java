package hr.app.manager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import hr.app.model.Apartment;

/**
 * @author Tomislav Ukić
 * 
 * The Manager program implements an application that
 * manages booking of an apartment
 * 
 *
 */
public class Manager {
	
	// default year and month for default homepage
	private int year = Calendar.getInstance().get(Calendar.YEAR);
	private final int month = 7;
	// viewable months
	private final String[] monthComboBoxArray = {"Lipanj", "Srpanj", "Kolovoz", "Rujan"};
	private String[] yearComboBoxArray;
			
	// stores all apartments 
	private List<Apartment> apartmentList = new LinkedList<>();
	Thread initDataThr;	// gets all data from db to manager
	private JFrame frame; // main frame
	private JComboBox<String> monthCmbBox;	// combobox for choosing month
	private JComboBox<String> yearCmbBox;
	private JPanel centralPanel; // parent panel to content panel in middle of frame
	private JPanel contentPanel; // displays all apartments
	private JPanel topPanel; // displays buttons on top of page
	private JButton newReservationBtn; // button for adding new reservation
	private JButton newApartmentBtn; //button for adding new apartment
	private JButton updateApartmentBtn; //button for eding existing apartment
	private JButton exportDataBtn; //button for exporting data to local file
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		new Printer();
		
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
		
		// starting thread for backuping data at end of program
		Thread backupDataStartThr = new Thread(new BackupData()); 
		backupDataStartThr.start();
	
	}

	/**
	 * Create the application.
	 */
	public Manager() {
		
		// starting thread for getting and initializing data from DB
		initDataThr = new Thread(new InitData(this));
		initDataThr.start();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// setting main frame parameters
		frame = new JFrame();
		frame.setBounds(100, 100, 1006, 634);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		// setting top panel which stores all top buttons
		topPanel = new JPanel();
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(1, 2, 0, 0));

		// initialising and setting top panel buttons parameters
		newReservationBtn = new JButton("Nova rezervacija");
		newReservationBtn.addActionListener(new ReservationClicked(this));		
		
		newApartmentBtn = new JButton("Novi apartman");
		newApartmentBtn.addActionListener(l -> new ApartmentFrame(this, false));
		
		updateApartmentBtn = new JButton("Uredi apartmane");
		updateApartmentBtn.addActionListener(l -> new ApartmentFrame(this, true));

		exportDataBtn = new JButton("Izvezi");
		exportDataBtn.addActionListener(l -> new ExportData(this));
		// top panel buttons added to panel in refreshContentPanel
				
		// adding central panel to frame
		centralPanel = new JPanel();
		frame.getContentPane().add(centralPanel, BorderLayout.CENTER);
		
		// setting rest of panels
		centralPanel.setLayout(new BorderLayout());
		contentPanel = new JPanel();
		monthCmbBox = new JComboBox<>(monthComboBoxArray);
		monthCmbBox.addActionListener(new MonthCmbBoxChanged(this));
		
		yearComboBoxArray = new String[8];
		for(int i = -3; i < 5; i++) {
			int tmp = year + i;
			yearComboBoxArray[i+3] = Integer.toString(tmp);
		}
		yearCmbBox = new JComboBox<>(yearComboBoxArray);
		yearCmbBox.setSelectedItem(Integer.toString(year));
		yearCmbBox.addActionListener(new MonthCmbBoxChanged(this));
		
		// adding content panel with scroll pane to central panel
		centralPanel.add(new JScrollPane(contentPanel), BorderLayout.CENTER);
		frame.add(centralPanel);
		
		// show loading screen while data is fething
		new LoadingScreen(this).show();
		
		
		// if closing main window start thread for backupdata
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				Thread backupDataFinishThr = new Thread(new BackupData());
				backupDataFinishThr.start();
		    }
		});
				
	}

	/**
	 * Adds apartment to manager's apartment list
	 * @param apartment is being add to apartment list
	 */
	public void addToApartmentList(Apartment apartment) {
		apartmentList.add(apartment);
	}
	
	/**
	 * Gets list of partments
	 * @return apartment list
	 */
	public List<Apartment> getApartmentList() {
		return apartmentList;
	}
	
	/**
	 * Gets central panel
	 * @return central panel
	 */
	public JPanel getCentralPanel() {
		return centralPanel;
	}
	
	/**
	 * Sets central panel
	 * @param centralPanel
	 */
	public void setCentralPanel(JPanel centralPanel) {
		this.centralPanel = centralPanel;
	}
	
	/**
	 * Gets month combo box
	 * @return
	 */
	public JComboBox<String> getMonthCmbBox() {
		return monthCmbBox;
	}

	/**
	 * Gets year combo box
	 * @return
	 */
	public JComboBox<String> getYearCmbBox() {
		return yearCmbBox;
	}


	/**
	 * Gets defualt year
	 * @return year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Gets default month
	 * @return month
	 */
	public int getMonth() {
		return month;
	}
	
	
	/**
	 * Gets main frame
	 * @return frame
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	/**
	 * Gets content panel
	 * @return content panel
	 */
	public JPanel getContentPanel() {
		return contentPanel;
	}
	
	/**
	 * Sets content panel
	 * @param contentPanel
	 */
	public void setContentPanel(JPanel contentPanel) {
		this.contentPanel = contentPanel;
	}
	
	/**
	 * Gets button for adding new reservation
	 * @return new reservation button
	 */
	public JButton getNewReservationBtn() {
		return newReservationBtn;
	}
	
	/**
	 * Gets button for adding new apartment
	 * @return new apartment button
	 */
	public JButton getNewApartmentBtn() {
		return newApartmentBtn;
	}
	
	/**
	 * Gets button for updating existing apartment
	 * @return update apartment button
	 */
	public JButton getUpdateApartmentBtn() {
		return updateApartmentBtn;
	}
	
	
	/**
	 * Gets button for exporting data
	 * @return export data button
	 */
	public JButton getExportDataBtn() {
		return exportDataBtn;
	}
	
	
	
	/**
	 * Gets top panel which contains buttons in the top of the frame
	 * @return top panel 
	 */
	public JPanel getTopPanel() {
		return topPanel;
	}
	
	
	/**
	 * Method doesn't make sense searches it searches for the apartment
	 * in the list and returns it if it is found otherwise returns null
	 * @param apartment
	 * @return apartment if apartment is in the apartment list 
	 * 			otherwise returns null
	 */
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
	
	/**
	 * Searches for apartment in list by its id
	 * @param id
	 * @return apartment if list contains that id otherwise
	 * 			returns new apartment with just id
	 */
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
	
	
	/**
	 * Makes String from apartments which is returned as 
	 * String array
	 * @return apartments string array every field
	 * 		corresponds to one apartment
	 */
	public String[] apartmentsToStringArray() {
		String[] apartmentsStringArray = new String[getApartmentList().size()];
		for(int i = 0; i < apartmentsStringArray.length; i++) {
			apartmentsStringArray[i] = getApartmentList().get(i).getName();
		}
		return apartmentsStringArray;
	}
	
	/**
	 * Finds apartment by name
	 * @param name
	 * @return apartment if apartment is found
	 * 		otherwise null
	 */
	public Apartment getApartmentByName(String name) {
		for(Apartment apartment : apartmentList) {
			if(apartment.getName().equals(name)) {
				return apartment;
			}
		}
		return null;
	}
	
	
	/**
	 * Clears apartments list 
	 */
	public void removeAllData() {
		apartmentList.clear();
	}
	
}
