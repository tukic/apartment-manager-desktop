package hr.app.manager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import hr.app.model.Apartment;
import hr.app.model.Reservation;



/**
 * 
 * Class collects and exports data
 * 
 * @author Tomislav Ukić
 *
 */
public class ExportData {
	
	/**
	 * 
	 * Constructor gets data and writes it to file
	 * 
	 * @param manager
	 */
	public ExportData(Manager manager) {
		
		List<Apartment> apartments = manager.getApartmentList(); // list of apartments
		String exportFolder = "exported_files";	// folder which stores exported files
		String fileName = exportFolder.concat("/").concat(LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
				.toString().concat(".txt"));	// file name is time of server with .txt suffix
		
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName)
				, StandardCharsets.UTF_8
				, StandardOpenOption.CREATE
				, StandardOpenOption.TRUNCATE_EXISTING
				, StandardOpenOption.WRITE)) {
			for (Apartment apartment : apartments) {
				List<Reservation> reservations = apartment.getReservationList();
				
				for (Reservation reservation : reservations) {
					String name = reservation.getTourists().getName();
					double price = reservation.getPricePerNight();
					
					writer.write(name + "," + price + ";");
				}
			}
			
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
}
