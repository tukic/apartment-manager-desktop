package hr.app.manager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class CalculationFrame {
	
	int numberOfDays;
	double pricePerNight, totalPrice, advancedPaymentAmount;
	public static double eurToHrk = 7.4;
	String advancedPaymentCurrency;
	JFrame frame;
	JPanel mainPanel, leftPanel, rightPanel;
	JButton printBtn;
	
	public CalculationFrame(JFrame parentFrame, int numberOfDays, double totalPrice,
			double pricePerNight, double advancedPaymentAmount, String advancedPaymentCurrency) {
		this.numberOfDays = numberOfDays;
		this.pricePerNight = pricePerNight;
		this.totalPrice = totalPrice;
		this.advancedPaymentAmount = advancedPaymentAmount;
		this.advancedPaymentCurrency = advancedPaymentCurrency;
		initialize();
		frame.pack();
		frame.setLocationRelativeTo(parentFrame);
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		//frame.setBounds(50, 50, 800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mainPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		leftPanel = new JPanel(new GridLayout(4, 0));
		rightPanel = new JPanel(new GridLayout(4, 0));

		if(advancedPaymentCurrency.equals("EUR")) {
			double forPayment = totalPrice - advancedPaymentAmount;
			String line1 = "  " + totalPrice;
			String line2 = "- " + advancedPaymentAmount;
			String line3 = "____________________________";
			String line4 = "  " + forPayment + " EUR";
			leftPanel.add(new JLabel(line1));
			leftPanel.add(new JLabel(line2));
			leftPanel.add(new JLabel(line3));
			leftPanel.add(new JLabel(line4));
			
			line1 = " " + totalPrice*eurToHrk;
			line2 = "- " + advancedPaymentAmount*eurToHrk;
			line4 = "  " + forPayment*eurToHrk + " HRK";
			
			rightPanel.add(new JLabel(line1));
			rightPanel.add(new JLabel(line2));
			rightPanel.add(new JLabel(line3));
			rightPanel.add(new JLabel(line4));
			
		} else {
			double forPayment = totalPrice - advancedPaymentAmount/eurToHrk;
			String line1 = "  " + totalPrice;
			String line2 = "- " + advancedPaymentAmount/eurToHrk;
			String line3 = "____________________________";
			String line4 = "  " + forPayment + " EUR";
			leftPanel.add(new JLabel(line1));
			leftPanel.add(new JLabel(line2));
			leftPanel.add(new JLabel(line3));
			leftPanel.add(new JLabel(line4));
			
			line1 = "  " + totalPrice*eurToHrk;
			line2 = "- " + advancedPaymentAmount;
			line4 = "  " + (totalPrice*eurToHrk - advancedPaymentAmount) + " HRK";
			rightPanel.add(new JLabel(line1));
			rightPanel.add(new JLabel(line2));
			rightPanel.add(new JLabel(line3));
			rightPanel.add(new JLabel(line4));
		}
		
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);
		frame.add(mainPanel);
		
		printBtn = new JButton("Ispiši");
		printBtn.addActionListener(l -> {
			try {
				Document document = new Document();
				PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
				document.open();
				Font font = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);
				Paragraph chunk;
					chunk = new Paragraph(pricePerNight + "×"
							+ numberOfDays + "=" + totalPrice, font);
					document.add(chunk);
					document.add(Chunk.NEWLINE);
					chunk = new Paragraph("\n-" + advancedPaymentAmount);
					document.add(chunk);
					document.add(Chunk.NEWLINE);
					double calculation = totalPrice-advancedPaymentAmount;
					chunk = new Paragraph("\n" + calculation);
					document.add(chunk);
					document.add(Chunk.NEWLINE);
					document.close();

			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		frame.add(printBtn, BorderLayout.PAGE_END);
		
	}

}
