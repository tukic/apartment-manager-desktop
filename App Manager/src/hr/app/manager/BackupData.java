package hr.app.manager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import com.smattme.MysqlExportService;

public class BackupData implements Runnable {

	@Override
	public void run() {
		Properties properties = new Properties();
		
		properties.setProperty(MysqlExportService.DB_NAME, "app_ukic");
		properties.setProperty(MysqlExportService.DB_USERNAME, "johto_db");
		properties.setProperty(MysqlExportService.DB_PASSWORD, "antejoni2007");
		properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING, "jdbc:mysql://85.10.205.173:3306/app_ukic");
		
		MysqlExportService mysqlExportService = new MysqlExportService(properties);
		
		try {
			
			//create directory backup if not exists;
			String backupStr = "backup";
			Path dirPathObj = Paths.get(backupStr);
	        if(!Files.exists(dirPathObj)) {
	            try {
	                // Creating The New Directory Structure
	                Files.createDirectories(dirPathObj);
	                System.out.println("! New Directory Successfully Created !");
	            } catch (IOException ioExceptionObj) {
	                System.out.println("Problem Occured While Creating The Directory Structure= " + ioExceptionObj.getMessage());
	            }
	        }
	            
			mysqlExportService.export();
			String fileName = backupStr.concat("/").concat(LocalDateTime.now()
					.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")).toString().concat(".txt"));
			String generatedSql = mysqlExportService.getGeneratedSql();
			System.out.println(generatedSql);
			System.out.println(fileName);
			try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName), StandardCharsets.UTF_8, 
					StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
				bw.write(generatedSql);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			
		}

	}

}
