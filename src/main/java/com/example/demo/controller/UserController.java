package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.usersService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private usersService us;

	@GetMapping("/profile")
	public String userProfile(Model model,Principal principal ){

		String username = principal.getName();
		Users user = us.getByUsername(username);

		int max = 0;

		List<Prescriptions> prescript = us.getPresByUsername(username);
		for(Prescriptions p : prescript){
			max = max + 1;
		}

		int maxReport = 0;

		List<lab_report> report = us.getAllReportsByUsername(username);
		for(lab_report r : report ){
			maxReport = maxReport + 1;
		}

		int maxRefer = 0;

		List<Referral> refer = us.getReferralByUsername(username);
		for(Referral re : refer){
			maxRefer = maxRefer + 1;
		}

		model.addAttribute("maxRefer",maxRefer);

		model.addAttribute("maxReport",maxReport);

		model.addAttribute("max",max);

		model.addAttribute("user",user);

		return "UserProfile";
	}

	@GetMapping("/addImage{id}")
	public String addImage(@PathVariable("id") long id,Model model){
		model.addAttribute("id",id);
		return "cameraman";
	}
	@PostMapping("/saveImage")
	public void saveImage(@RequestBody String image,@RequestParam("patientId") String patientId,Model model) {

		System.out.println(patientId);
		try {
			long number = Long.parseLong(patientId);
			Users user = us.geUserById(number);
			String imageData = image.substring(image.indexOf(",") + 1);
			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageData);
			System.out.println(imageBytes);
			user.setProfilephoto(imageBytes);
			String image1 = Base64.getEncoder().encodeToString(imageBytes);
//			String imageData1 = image.substring(image1.indexOf(",") + 1);
			byte[] decodedBytes = Base64.getDecoder().decode(image1);
			String base64Encoded = Base64.getEncoder().encodeToString(decodedBytes).replace("imagedataimage/pngbase64", "");
			user.setBase64Image(base64Encoded);
			us.saveUser(user);

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid patient ID");
		}

		model.addAttribute("message","proile photo saved successfully!");
	}
	
	@PostMapping("/appointment-sheduled")
	public String sheduled(Model model,@ModelAttribute Appoinments appoint,@RequestParam("date") String date,
			@RequestParam("mobile") String mobile,@RequestParam("time") String time,
			BindingResult result) {
	    
	    if (result.hasErrors()) {
	    	model.addAttribute("message", "Enter details correctly");
            return "user";
        }
	    
	    List<Appoinments> u1 = us.getall();
	    
//	    List<String> times = new ArrayList<>();
//	    
//	    List<String> dates = new ArrayList<>();
//	    
//	    for(Appoinments s : u1) {
//	    	
//	    	String t1 = s.getTime();
//	    	String d1 = s.getDate();
//	    	times.add(t1);
//	    	times.add(d1);
//	    }
	    

		    us.saveAppoinment(appoint);
		    String ACCOUNT_SID = "ACe2ffc0e7ad24cb03314f3322bae7c0c5";
		    String AUTH_TOKEN = "6d2b958052b3e0614f47b302bed25690";
		    String FROM_PHONE_NUMBER = "+12282313744";
		    
		    com.twilio.Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		    
		    com.twilio.rest.api.v2010.account.Message twilioMessage = com.twilio.rest.api.v2010.account.Message.creator(
		    		ACCOUNT_SID,new com.twilio.type.PhoneNumber("+"+mobile),
		    		new com.twilio.type.PhoneNumber(FROM_PHONE_NUMBER),
		    		"Hello, your appointment has been scheduled successfully!").create();
		    
		    System.out.println("SMS sent successfully " + twilioMessage.getSid());
	    	
	    	model.addAttribute("message","Appoinment Sheduled Successfully! check your mobile for confirmation");
	    	return "user";
	    	
	
	}
	
	@GetMapping("/My-Appointment")
	public String appointments(Principal principal,Model model) {
		
		String username = principal.getName();
		
		Users user = us.getByUsername(username);
		String name = user.getFullName();
		
		List<Appoinments> appoint = us.getByName(name);
		
		model.addAttribute("appoint", appoint);
		
		return "appoint";
	}
	
	@GetMapping("/Certificates")
	public String certificate(Model model,Principal principal) {
		
		String username = principal.getName();
		List<Certificates> certify = us.getCertByUsername(username);
		model.addAttribute("certify", certify);
		
		return "certifications";
	}
	
	@GetMapping("/Prescriptions")
	public String prescription(Model model,Principal principal) {
		
		String username = principal.getName();
		List<Prescriptions> certify = us.getPresByUsername(username);
		model.addAttribute("certify", certify);
		
		return "Prescriptions";
	}
	
	@GetMapping("/LabReports")
	public String LabReports(Model model,Principal principal) {
		
		String username = principal.getName();
		List<lab_report> certify = us.getReportByUsername(username);
		model.addAttribute("certify", certify);
		
		return "LabReports";
	}
	
	@GetMapping("/Receipts")
	public String Receipts(Model model,Principal principal) {
		
		String username = principal.getName();
		List<Receipt> certify = us.getReceiptByUsername(username);
		model.addAttribute("certify", certify);
		
		return "Receipt";
	}
	
	@GetMapping("/ReferNote")
	public String Refer(Model model,Principal principal) {
		
		String username = principal.getName();
		List<Referral> certify = us.getReferralByUsername(username);
		model.addAttribute("certify", certify);
		
		return "Refer";
	}
	
	@RequestMapping("/cerificates/download/{id}")
	@ResponseBody
	public ResponseEntity<InputStreamResource> download(HttpServletResponse response, @PathVariable("id") long id) throws IOException, DocumentException {

		Certificates certificate = us.getcertById(id);
		String doctor = certificate.getDoctor();
		Users doc = us.getByFullName(doctor);

		String patient = certificate.getPatientName();
		Users patiento = us.getByFullName(patient);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, out);
		document.open();

		byte[] imageUrl1 = doc.getLogo();
		Image image1 = Image.getInstance(imageUrl1);
		image1.scaleAbsolute(50.0f, 25.0f);
		image1.setAlignment(Element.ALIGN_LEFT);


		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
		Paragraph titlePara = new Paragraph();
		titlePara.setAlignment(Element.ALIGN_LEFT);
		titlePara.add(new Chunk(image1, 0, 0));
		titlePara.add(new Chunk("                            " + doc.getHospital(), font));
		document.add(titlePara);

		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Paragraph SubPara = new Paragraph(doc.getAddress(), font1);
		SubPara.setAlignment(Element.ALIGN_CENTER);
		document.add(SubPara);

		Font fonts = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Paragraph SubPara2 = new Paragraph("Mob no."+doc.getCountrycode()+doc.getPhone()+"     Email : "+doc.getEmail(), fonts);
		SubPara2.setAlignment(Element.ALIGN_CENTER);
		document.add(SubPara2);

		document.add(new Chunk("\n"));
//        Chunk line = new Chunk(new LineSeparator());
//        document.add(line);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		float[] colWidths = {60f,40f};
		table.setWidths(colWidths);

		PdfPCell leftCell = new PdfPCell();
		leftCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);

		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 12);
		Paragraph doctoro = new Paragraph("PATIENT NAME :" + patiento.getTitle()+patiento.getFullName(), font2);
		doctoro.setAlignment(Element.ALIGN_LEFT);
		leftCell.addElement(doctoro);

		Font font4 = FontFactory.getFont(FontFactory.HELVETICA, 12);
		Paragraph phone = new Paragraph("ADDRESS :" + patiento.getAddress(), font4);
		phone.setAlignment(Element.ALIGN_LEFT);
		leftCell.addElement(phone);

		PdfPCell rightCell = new PdfPCell();
		rightCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);

		Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 11);
		Paragraph date = new Paragraph("DATE :" + certificate.getIssueDate(), font3);
		date.setAlignment(Element.ALIGN_RIGHT);
		rightCell.addElement(date);

		Font font5 = FontFactory.getFont(FontFactory.HELVETICA, 11);
		Paragraph email = new Paragraph("AGE/SEX : " + patiento.getGender()+","+patiento.getAge()+" years", font5);
		email.setAlignment(Element.ALIGN_RIGHT);
		rightCell.addElement(email);

		table.addCell(leftCell);
		table.addCell(rightCell);

		document.add(table);

		String fontPath1 = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
		BaseFont customFont1 = BaseFont.createFont(fontPath1, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);

		// Add table headers
		Font dataFont1 = new Font(customFont1, 12);

		document.add(new Chunk("\n"));
		document.add(new Chunk("\n"));
		document.add(new Chunk("\n"));

		if(certificate.getCertificateName().equals("Medical Certificate")) {

			String fontPath2 = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
			BaseFont customFont2 = BaseFont.createFont(fontPath2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);

			Font dataFont2 = new Font(customFont2, 15);

			Font font6 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
			Paragraph CertificateName = new Paragraph(certificate.getCertificateName() , font6);
			CertificateName.setAlignment(Element.ALIGN_CENTER);
			document.add(CertificateName);

			document.add(new Chunk("\n"));

			Paragraph firstLine = new Paragraph("This is to certify that the individual known are "+certificate.getPatientName()+" ,has " , dataFont2);
			firstLine.setAlignment(Element.ALIGN_CENTER);
			document.add(firstLine);

			Paragraph SecondLine = new Paragraph("and he is currently suffering from a _______________." , dataFont2);
			SecondLine.setAlignment(Element.ALIGN_CENTER);
			document.add(SecondLine);

			Paragraph ThirdLine = new Paragraph("The examiner has advised that for the sake of the individuals ", dataFont2);
			ThirdLine.setAlignment(Element.ALIGN_CENTER);
			document.add(ThirdLine);

			Paragraph FourthLine = new Paragraph("overall health, he should be relieved ", dataFont2);
			FourthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(FourthLine);

			Paragraph FifthLine = new Paragraph("of his duties for duration of _________________." , dataFont2);
			FifthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(FifthLine);

			Paragraph SixthLine = new Paragraph("The patient is advised to refrain from engaging in strenuous activities" , dataFont2);
			SixthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(SixthLine);

			Paragraph SeventhLine = new Paragraph("until fully recovered." , dataFont2);
			SeventhLine.setAlignment(Element.ALIGN_CENTER);
			document.add(SeventhLine);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph signature = new Paragraph("Doctor Name And Signature", dataFont2);
			signature.setAlignment(Element.ALIGN_RIGHT);
			document.add(signature);

			document.add(new Chunk("\n"));
			byte[] imageUrl = doc.getSign();
			Image image = Image.getInstance(imageUrl);
			image.scaleAbsolute(100.0f, 55.0f);
			image.setAlignment(Element.ALIGN_RIGHT);
			document.add(image);

			Paragraph docName = new Paragraph(doc.getTitle()+" "+doc.getFullName(), dataFont2);
			docName.setAlignment(Element.ALIGN_RIGHT);
			document.add(docName);

			Paragraph degree = new Paragraph(doc.getQualification(), dataFont2);
			degree.setAlignment(Element.ALIGN_RIGHT);
			document.add(degree);


		}

		if(certificate.getCertificateName().equals("Birth Certificate")) {

			String fontPath2 = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
			BaseFont customFont2 = BaseFont.createFont(fontPath2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);
			Font dataFont2 = new Font(customFont2, 15);

			Font font6 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
			Paragraph CertificateName = new Paragraph(certificate.getCertificateName() , font6);
			CertificateName.setAlignment(Element.ALIGN_CENTER);
			document.add(CertificateName);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph firstLine = new Paragraph("This is to certify that the following child was born:" , dataFont2);
			firstLine.setAlignment(Element.ALIGN_CENTER);
			document.add(firstLine);

			Paragraph SecondLine = new Paragraph("Name: ____________________________________", dataFont2);
			SecondLine.setAlignment(Element.ALIGN_CENTER);
			document.add(SecondLine);

			Paragraph ThirdLine = new Paragraph("Date of Birth: ___________________", dataFont2);
			ThirdLine.setAlignment(Element.ALIGN_CENTER);
			document.add(ThirdLine);

			Paragraph FourthLine = new Paragraph("Place of Birth: ___________________", dataFont2);
			FourthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(FourthLine);

			Paragraph FifthLine = new Paragraph("Gender: _____________", dataFont2);
			FifthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(FifthLine);

			Paragraph SixthLine = new Paragraph("Father's Name: _________________________________", dataFont2);
			SixthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(SixthLine);

			Paragraph SeventhLine = new Paragraph("Mother's Name: _________________________________", dataFont2);
			SeventhLine.setAlignment(Element.ALIGN_CENTER);
			document.add(SeventhLine);

			Paragraph eighthLine = new Paragraph("Issue Date : "+certificate.getIssueDate(), dataFont2);
			eighthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(eighthLine);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph signature = new Paragraph("Doctor Name And Signature", dataFont2);
			signature.setAlignment(Element.ALIGN_RIGHT);
			document.add(signature);

			document.add(new Chunk("\n"));
			byte[] imageUrl = doc.getSign();
			Image image = Image.getInstance(imageUrl);
			image.scaleAbsolute(100.0f, 55.0f);
			image.setAlignment(Element.ALIGN_RIGHT);
			document.add(image);

			Paragraph docName = new Paragraph(doc.getTitle()+" "+doc.getFullName(), dataFont2);
			docName.setAlignment(Element.ALIGN_RIGHT);
			document.add(docName);

			Paragraph degree = new Paragraph(doc.getQualification(), dataFont2);
			degree.setAlignment(Element.ALIGN_RIGHT);
			document.add(degree);


		}

		if(certificate.getCertificateName().equals("Medical Fitness Certificate")) {

			String fontPath2 = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
			BaseFont customFont2 = BaseFont.createFont(fontPath2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);
			Font dataFont2 = new Font(customFont2, 15);

			Font font6 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
			Paragraph CertificateName = new Paragraph(certificate.getCertificateName() , font6);
			CertificateName.setAlignment(Element.ALIGN_CENTER);
			document.add(CertificateName);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph firstLine = new Paragraph("This is to certify that "+certificate.getPatientName()+" has undergone a medical examination" , dataFont2);
			firstLine.setAlignment(Element.ALIGN_CENTER);
			document.add(firstLine);

			Paragraph SecondLine = new Paragraph("and is found to be medically fit with the following details : " , dataFont2);
			SecondLine.setAlignment(Element.ALIGN_CENTER);
			document.add(SecondLine);

			Paragraph ThirdLine = new Paragraph("Date of Examination: "+certificate.getIssueDate() , dataFont2);
			ThirdLine.setAlignment(Element.ALIGN_CENTER);
			document.add(ThirdLine);

			Paragraph FourthLine = new Paragraph("Height: _______________" , dataFont2);
			FourthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(FourthLine);

			Paragraph FifthLine = new Paragraph("Weight: _______________" , dataFont2);
			FifthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(FifthLine);

			Paragraph SixthLine = new Paragraph("Blood Pressure: _______________" , dataFont2);
			SixthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(SixthLine);

			Paragraph SeventhLine = new Paragraph("Heart Rate: __________________" , dataFont2);
			SeventhLine.setAlignment(Element.ALIGN_CENTER);
			document.add(SeventhLine);

			Paragraph EighthLine = new Paragraph("Respiratory Rate: ___________" , dataFont2);
			EighthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(EighthLine);

			Paragraph NinthLine = new Paragraph("The patient is deemed medically fit for the specified purpose." , dataFont2);
			NinthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(NinthLine);

			Paragraph tenthLine = new Paragraph("Issue Date : "+certificate.getIssueDate() , dataFont2);
			tenthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(tenthLine);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph signature = new Paragraph("Doctor Name And Signature", dataFont2);
			signature.setAlignment(Element.ALIGN_RIGHT);
			document.add(signature);

			document.add(new Chunk("\n"));
			byte[] imageUrl = doc.getSign();
			Image image = Image.getInstance(imageUrl);
			image.scaleAbsolute(100.0f, 55.0f);
			image.setAlignment(Element.ALIGN_RIGHT);
			document.add(image);

			Paragraph docName = new Paragraph(doc.getTitle()+" "+doc.getFullName(), dataFont2);
			docName.setAlignment(Element.ALIGN_RIGHT);
			document.add(docName);

			Paragraph degree = new Paragraph(doc.getQualification(), dataFont2);
			degree.setAlignment(Element.ALIGN_RIGHT);
			document.add(degree);

		}

		if(certificate.getCertificateName().equals("Discharge Certificate")) {

			String fontPath2 = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
			BaseFont customFont2 = BaseFont.createFont(fontPath2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);
			Font dataFont2 = new Font(customFont2, 15);

			Font font6 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
			Paragraph CertificateName = new Paragraph(certificate.getCertificateName() , font6);
			CertificateName.setAlignment(Element.ALIGN_CENTER);
			document.add(CertificateName);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph firstLine = new Paragraph("This is to certify that "+certificate.getPatientName()+" has been discharged from" , dataFont2);
			firstLine.setAlignment(Element.ALIGN_CENTER);
			document.add(firstLine);

			Paragraph secondLine = new Paragraph(doc.getHospital()+" on ______________ after successful treatment" , dataFont2);
			secondLine.setAlignment(Element.ALIGN_CENTER);
			document.add(secondLine);

			Paragraph thirdLine = new Paragraph("of ____________________________." , dataFont2);
			thirdLine.setAlignment(Element.ALIGN_CENTER);
			document.add(thirdLine);

			Paragraph fourthLine = new Paragraph("The patient's condition has significantly improved, and they are deemed fit for discharge. The patient is advised to follow the prescribed medication and attend follow-up appointments as advised." , dataFont2);
			fourthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(fourthLine);

			Paragraph fifthLine = new Paragraph("Issue Date : "+certificate.getIssueDate() , dataFont2);
			fifthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(fifthLine);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph signature = new Paragraph("Doctor Name And Signature", dataFont2);
			signature.setAlignment(Element.ALIGN_RIGHT);
			document.add(signature);

			document.add(new Chunk("\n"));
			byte[] imageUrl = doc.getSign();
			Image image = Image.getInstance(imageUrl);
			image.scaleAbsolute(100.0f, 55.0f);
			image.setAlignment(Element.ALIGN_RIGHT);
			document.add(image);

			Paragraph docName = new Paragraph(doc.getTitle()+" "+doc.getFullName(), dataFont2);
			docName.setAlignment(Element.ALIGN_RIGHT);
			document.add(docName);

			Paragraph degree = new Paragraph(doc.getQualification(), dataFont2);
			degree.setAlignment(Element.ALIGN_RIGHT);
			document.add(degree);

		}

		if(certificate.getCertificateName().equals("Death Certificate")) {

			String fontPath2 = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
			BaseFont customFont2 = BaseFont.createFont(fontPath2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);
			Font dataFont2 = new Font(customFont2, 15);

			Font font6 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
			Paragraph CertificateName = new Paragraph(certificate.getCertificateName() , font6);
			CertificateName.setAlignment(Element.ALIGN_CENTER);
			document.add(CertificateName);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph firstLine = new Paragraph("This is to certify that "+certificate.getPatientName()+" passed away on ___________. "
					+ "The cause of death is reported as _________________. "
					+ "The deceased was _____ years old at the time of death and was born on _________. "
					+ "The deceased's gender was _________. "
					+ "This certificate is issued based on the available records and in accordance with applicable regulations." , dataFont2);
			firstLine.setAlignment(Element.ALIGN_CENTER);
			document.add(firstLine);

			Paragraph SecondLine = new Paragraph("Issue Date : "+certificate.getIssueDate() , dataFont2);
			SecondLine.setAlignment(Element.ALIGN_RIGHT);
			document.add(SecondLine);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph signature = new Paragraph("Doctor Name And Signature", dataFont2);
			signature.setAlignment(Element.ALIGN_RIGHT);
			document.add(signature);

			document.add(new Chunk("\n"));
			byte[] imageUrl = doc.getSign();
			Image image = Image.getInstance(imageUrl);
			image.scaleAbsolute(100.0f, 55.0f);
			image.setAlignment(Element.ALIGN_RIGHT);
			document.add(image);

			Paragraph docName = new Paragraph(doc.getTitle()+" "+doc.getFullName(), dataFont2);
			docName.setAlignment(Element.ALIGN_RIGHT);
			document.add(docName);

			Paragraph degree = new Paragraph(doc.getQualification(), dataFont2);
			degree.setAlignment(Element.ALIGN_RIGHT);
			document.add(degree);

		}

		if(certificate.getCertificateName().equals("Surgery Certificate")) {

			String fontPath2 = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
			BaseFont customFont2 = BaseFont.createFont(fontPath2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);
			Font dataFont2 = new Font(customFont2, 15);

			Font font6 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
			Paragraph CertificateName = new Paragraph(certificate.getCertificateName() , font6);
			CertificateName.setAlignment(Element.ALIGN_CENTER);
			document.add(CertificateName);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph firstLine = new Paragraph("This is to certify that "+certificate.getPatientName()+" underwent surgery on _________. "
					+ "The surgical procedure performed was ______________________. "
					+ "The surgery was performed by __________________ at "+doc.getHospital()+". The patient's condition and progress were monitored closely, "
					+ "and appropriate care and treatment were provided.The surgery was successful, "
					+ "and the patient is currently undergoing post-operative care." , dataFont2);
			firstLine.setAlignment(Element.ALIGN_CENTER);
			document.add(firstLine);

			Paragraph SecondLine = new Paragraph("Issue Date : "+certificate.getIssueDate() , dataFont2);
			SecondLine.setAlignment(Element.ALIGN_RIGHT);
			document.add(SecondLine);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph signature = new Paragraph("Doctor Name And Signature", dataFont2);
			signature.setAlignment(Element.ALIGN_RIGHT);
			document.add(signature);

			document.add(new Chunk("\n"));
			byte[] imageUrl = doc.getSign();
			Image image = Image.getInstance(imageUrl);
			image.scaleAbsolute(100.0f, 55.0f);
			image.setAlignment(Element.ALIGN_RIGHT);
			document.add(image);

			Paragraph docName = new Paragraph(doc.getTitle()+" "+doc.getFullName(), dataFont2);
			docName.setAlignment(Element.ALIGN_RIGHT);
			document.add(docName);

			Paragraph degree = new Paragraph(doc.getQualification(), dataFont2);
			degree.setAlignment(Element.ALIGN_RIGHT);
			document.add(degree);

		}

		if(certificate.getCertificateName().equals("Immunization Certificate")) {

			String fontPath2 = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
			BaseFont customFont2 = BaseFont.createFont(fontPath2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);
			Font dataFont2 = new Font(customFont2, 15);

			Font font6 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
			Paragraph CertificateName = new Paragraph(certificate.getCertificateName() , font6);
			CertificateName.setAlignment(Element.ALIGN_CENTER);
			document.add(CertificateName);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph firstLine = new Paragraph("This is to certify that "+certificate.getPatientName()+" has received the following " , dataFont2);
			firstLine.setAlignment(Element.ALIGN_CENTER);
			document.add(firstLine);

			Paragraph secondLine = new Paragraph("immunizations:" , dataFont2);
			secondLine.setAlignment(Element.ALIGN_CENTER);
			document.add(secondLine);

			Paragraph thirdLine = new Paragraph("- Vaccine 1 on _____________" , dataFont2);
			thirdLine.setAlignment(Element.ALIGN_CENTER);
			document.add(thirdLine);

			Paragraph fourthLine = new Paragraph("- Vaccine 2 on _____________" , dataFont2);
			fourthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(fourthLine);

			Paragraph fifthLine = new Paragraph("- Vaccine 3 on _____________" , dataFont2);
			fifthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(fifthLine);

			Paragraph sixthLine = new Paragraph("The immunizations were administered by _____________________ at "+doc.getHospital()+"." , dataFont2);
			sixthLine.setAlignment(Element.ALIGN_CENTER);
			document.add(sixthLine);

			Paragraph SecondLine = new Paragraph("Issue Date : "+certificate.getIssueDate() , dataFont2);
			SecondLine.setAlignment(Element.ALIGN_RIGHT);
			document.add(SecondLine);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph signature = new Paragraph("Doctor Name And Signature", dataFont2);
			signature.setAlignment(Element.ALIGN_RIGHT);
			document.add(signature);

			document.add(new Chunk("\n"));
			byte[] imageUrl = doc.getSign();
			Image image = Image.getInstance(imageUrl);
			image.scaleAbsolute(100.0f, 55.0f);
			image.setAlignment(Element.ALIGN_RIGHT);
			document.add(image);

			Paragraph docName = new Paragraph(doc.getTitle()+" "+doc.getFullName(), dataFont2);
			docName.setAlignment(Element.ALIGN_RIGHT);
			document.add(docName);

			Paragraph degree = new Paragraph(doc.getQualification(), dataFont2);
			degree.setAlignment(Element.ALIGN_RIGHT);
			document.add(degree);

		}

		if(certificate.getCertificateName().equals("Organ Donor Card")) {

			String fontPath2 = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
			BaseFont customFont2 = BaseFont.createFont(fontPath2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);
			Font dataFont2 = new Font(customFont2, 15);

			Font font6 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
			Paragraph CertificateName = new Paragraph(certificate.getCertificateName() , font6);
			CertificateName.setAlignment(Element.ALIGN_CENTER);
			document.add(CertificateName);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph firstLine = new Paragraph("I, "+certificate.getPatientName()+", hereby declare my wish to be an organ donor. "
					+ "I authorize the donation of any of my organs or tissues,"
					+ "in accordance with applicable laws and regulations,"
					+ "to be used for transplantation purposes,"
					+ "with the intention of saving or enhancing the lives of others." , dataFont2);
			firstLine.setAlignment(Element.ALIGN_CENTER);
			document.add(firstLine);

			document.add(new Chunk("\n"));

			Paragraph secondLine = new Paragraph("Signed: _____________________________" , dataFont2);
			secondLine.setAlignment(Element.ALIGN_RIGHT);
			document.add(secondLine);

			Paragraph thirdLine = new Paragraph("Date: _______________________________" , dataFont2);
			thirdLine.setAlignment(Element.ALIGN_RIGHT);
			document.add(thirdLine);

			Paragraph SecondLine = new Paragraph("Issue Date : "+certificate.getIssueDate() , dataFont2);
			SecondLine.setAlignment(Element.ALIGN_RIGHT);
			document.add(SecondLine);

			document.add(new Chunk("\n"));
			document.add(new Chunk("\n"));

			Paragraph signature = new Paragraph("Doctor Name And Signature", dataFont2);
			signature.setAlignment(Element.ALIGN_RIGHT);
			document.add(signature);

			document.add(new Chunk("\n"));
			byte[] imageUrl = doc.getSign();
			Image image = Image.getInstance(imageUrl);
			image.scaleAbsolute(100.0f, 55.0f);
			image.setAlignment(Element.ALIGN_RIGHT);
			document.add(image);

			Paragraph docName = new Paragraph(doc.getTitle()+" "+doc.getFullName(), dataFont2);
			docName.setAlignment(Element.ALIGN_RIGHT);
			document.add(docName);

			Paragraph degree = new Paragraph(doc.getQualification(), dataFont2);
			degree.setAlignment(Element.ALIGN_RIGHT);
			document.add(degree);

		}


		document.close();


		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());

		HttpHeaders httpheaders = new HttpHeaders();
		httpheaders.add("Content-Disposition", "inline:file=prescript.pdf");

		return ResponseEntity
				.ok()
				.headers(httpheaders)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(byteArrayInputStream));
		
	}
	
	@GetMapping("/prescriptions/download/{id}")
	@ResponseBody
	public ResponseEntity<InputStreamResource> preDownload(HttpServletResponse response, @PathVariable("id") long id,Principal principal) throws IOException, DocumentException {

		Prescriptions prescript = us.getById(id);

		String doctor = prescript.getDoctor();
		Users doc = us.getByFullName(doctor);

		String patient = principal.getName();
		Users patiento = us.getByUsername(patient);

		String address = prescript.getAddress().replace(" ", ",");
		String[] add = address.split(",");

		String instruct = prescript.getInstructions();
		String[] indata = instruct.split(",");

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, out);
		document.open();

		byte[] imageUrl1 = doc.getLogo();
		Image image1 = Image.getInstance(imageUrl1);
		image1.scaleAbsolute(50.0f, 25.0f);
		image1.setAlignment(Element.ALIGN_LEFT);


		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
		Paragraph titlePara = new Paragraph();
		titlePara.setAlignment(Element.ALIGN_LEFT);
		titlePara.add(new Chunk(image1, 0, 0));
		titlePara.add(new Chunk("                            " + doc.getHospital(), font));
		document.add(titlePara);

		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Paragraph SubPara = new Paragraph(doc.getAddress(), font1);
		SubPara.setAlignment(Element.ALIGN_CENTER);
		document.add(SubPara);

		Font fonto = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Paragraph SubParapm = new Paragraph(doc.getTitle()+" "+doc.getFullName()+"("+doc.getQualification()+")", fonto);
		SubParapm.setAlignment(Element.ALIGN_CENTER);
		document.add(SubParapm);

		Font fonts = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Paragraph SubPara2 = new Paragraph("Mob no."+doc.getCountrycode()+doc.getPhone()+"     Email : "+doc.getEmail(), fonts);
		SubPara2.setAlignment(Element.ALIGN_CENTER);
		document.add(SubPara2);

		document.add(new Chunk("\n"));
//        Chunk line = new Chunk(new LineSeparator());
//        document.add(line);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		float[] colWidths = {60f,40f};
		table.setWidths(colWidths);

		PdfPCell leftCell = new PdfPCell();
		leftCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		leftCell.setPaddingBottom(5.0f);

		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 12);
		Paragraph doctoro = new Paragraph("PATIENT NAME :" + patiento.getTitle()+patiento.getFullName(), font2);
		doctoro.setAlignment(Element.ALIGN_LEFT);
		leftCell.addElement(doctoro);

		Font font4 = FontFactory.getFont(FontFactory.HELVETICA, 12);
		Paragraph phone = new Paragraph("ADDRESS :" + patiento.getAddress(), font4);
		phone.setAlignment(Element.ALIGN_LEFT);
		leftCell.addElement(phone);

		PdfPCell rightCell = new PdfPCell();
		rightCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		rightCell.setPaddingBottom(5.0f);

		Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 11);
		Paragraph date = new Paragraph("DATE :" + prescript.getDate(), font3);
		date.setAlignment(Element.ALIGN_RIGHT);
		rightCell.addElement(date);

		Font font5 = FontFactory.getFont(FontFactory.HELVETICA, 11);
		Paragraph email = new Paragraph("AGE/SEX : " + patiento.getGender()+","+patiento.getAge()+" years", font5);
		email.setAlignment(Element.ALIGN_RIGHT);
		rightCell.addElement(email);

		table.addCell(leftCell);
		table.addCell(rightCell);

		document.add(table);

//        Chunk line2 = new Chunk(new LineSeparator());
//        document.add(line2);
		document.add(new Chunk("\n"));

		String fontPath1 = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
		BaseFont customFont1 = BaseFont.createFont(fontPath1, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);

		Font dataFont1 = new Font(customFont1, 10);

		if(prescript.getComplaint() != null) {
			// Add table headers


			Font font6 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
			Paragraph patientum = new Paragraph("PRESENT COMPLAINT: ", font6);
			patientum.setAlignment(Element.ALIGN_LEFT);
			document.add(patientum);

			Font font9 = FontFactory.getFont(FontFactory.HELVETICA, 10);
			String complaints = prescript.getComplaint();
			String[] splitted = complaints.split(",");
			for (String s : splitted) {

				Paragraph complaint = new Paragraph(s.replace("||", " FROM LAST "), font9);
				complaint.setAlignment(Element.ALIGN_LEFT);
				document.add(complaint);
			}

		}
		if(prescript.getObservations() != null) {
			Font font7 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
			Paragraph patientAddress = new Paragraph("OBSERVATIONS: ", font7);
			patientAddress.setAlignment(Element.ALIGN_LEFT);
			document.add(patientAddress);

			Font font10 = FontFactory.getFont(FontFactory.HELVETICA, 10);
			String observations = prescript.getObservations();
			String[] SplittedObservations = observations.split(",");
			for (String s1 : SplittedObservations) {
				Paragraph observation = new Paragraph(s1.replace("||", " : "), font10);
				observation.setAlignment(Element.ALIGN_LEFT);
				document.add(observation);
			}

		}
		document.add(new Chunk("\n"));

		Font font8 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
		Paragraph RX = new Paragraph("Rx , ", font8);
		RX.setAlignment(Element.ALIGN_LEFT);
		document.add(RX);

		document.add(new Chunk("\n"));

		PdfPTable table2 = new PdfPTable(3);
		table2.setWidthPercentage(100);
		table2.setPaddingTop(25.2f);
		float[] columnWidths = {30f, 40f, 30f};
		table2.setWidths(columnWidths);
//	    float[] columnWidths = {100f, 100f, 100f, 100f}; // Adjust the values as per your requirements
//	    table2.setTotalWidth(columnWidths);
//
//	    // Set individual column widths
//	    table2.setWidths(columnWidths);

		// Add table headers
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
		addTableCell(table2, "Medicine", headerFont);
		addTableCell(table2, "Dosage", headerFont);
		addTableCell(table2, "Days", headerFont);


		String fontPath = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
		BaseFont customFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);


		// Add table headers
		Font dataFont = new Font(customFont, 10);
		String medicine = prescript.getMedicines();
		String[] medics = medicine.split(",,");
		for (String medico : medics) {

			String[] medioData = medico.split("\\|\\|");
			for(String md : medioData) {
				String[] dosages = md.split("/");

				addTableCell(table2, md, dataFont);

			}

		}

		document.add(table2);

		document.add(new Chunk("\n"));
//        document.add(new Chunk("\n"));

		PdfPTable table3 = new PdfPTable(2);
		table3.setWidthPercentage(100);

		PdfPCell leftCell3 = new PdfPCell();
		leftCell3.setBorder(Rectangle.NO_BORDER);

		Paragraph intro1 = new Paragraph("Instructions :", dataFont1);
		intro1.setAlignment(Element.ALIGN_LEFT);
		document.add(intro1);


		for(String in : indata) {
			Paragraph intro = new Paragraph("* "+in, dataFont1);
			intro.setAlignment(Element.ALIGN_LEFT);
			document.add(intro);
		}

		PdfPCell rightCell3 = new PdfPCell();
		rightCell3.setBorder(Rectangle.NO_BORDER);

		Paragraph signature = new Paragraph("Doctor Name And Signature", dataFont1);
		signature.setAlignment(Element.ALIGN_RIGHT);
		document.add(signature);

		document.add(new Chunk("\n"));
		byte[] imageUrl = doc.getSign();
		Image image = Image.getInstance(imageUrl);
		image.scaleAbsolute(100.0f, 55.0f);
		image.setAlignment(Element.ALIGN_RIGHT);
		document.add(image);

		Paragraph docName = new Paragraph(doc.getTitle()+" "+doc.getFullName(), dataFont1);
		docName.setAlignment(Element.ALIGN_RIGHT);
		document.add(docName);

		Paragraph degree = new Paragraph(doc.getQualification(), dataFont1);
		degree.setAlignment(Element.ALIGN_RIGHT);
		document.add(degree);

		table3.addCell(leftCell3);
		table3.addCell(rightCell3);
		document.add(table3);

		document.close();


		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());

		HttpHeaders httpheaders = new HttpHeaders();
		httpheaders.add("Content-Disposition", "inline:file=prescript.pdf");

		return ResponseEntity
				.ok()
				.headers(httpheaders)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(byteArrayInputStream));
		
	}

	private void addTableCell(PdfPTable table2, String string, Font headerFont) {
		PdfPCell cell = new PdfPCell(new Phrase(string,headerFont));
		cell.setPadding(10.0f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		table2.addCell(cell);

	}
	
	@GetMapping("/reports/download/{id}")
    public ResponseEntity<byte[]> downloadLabReport(@PathVariable Long id) throws FileNotFoundException {
        lab_report labReport = us.getReportById(id);
        
        byte[] fileData = labReport.getFile();
        String fileName = labReport.getFileName();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return  ResponseEntity.ok()
                .headers(headers)
                .body(fileData);
    }
    
    @GetMapping("/receipts/download/{id}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadReceipt(@PathVariable("id") long id, HttpServletResponse response) throws IOException, DocumentException {

		Receipt receipt = us.getByReceiptId(id);

		String doctor = receipt.getDoctor();
		Users doc = us.getByFullName(doctor);

		String patient = receipt.getName();
		Users patiento = us.getByFullName(patient);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, out);
		document.open();

		byte[] imageUrl1 = doc.getLogo();
		Image image1 = Image.getInstance(imageUrl1);
		image1.scaleAbsolute(50.0f, 25.0f);
		image1.setAlignment(Element.ALIGN_LEFT);


		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
		Paragraph titlePara = new Paragraph();
		titlePara.setAlignment(Element.ALIGN_LEFT);
		titlePara.add(new Chunk(image1, 0, 0));
		titlePara.add(new Chunk("                            " + doc.getHospital(), font));
		document.add(titlePara);

		Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Paragraph SubPara = new Paragraph(doc.getAddress(), font1);
		SubPara.setAlignment(Element.ALIGN_CENTER);
		document.add(SubPara);

		Font fonts = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Paragraph SubPara2 = new Paragraph("Mob no."+doc.getCountrycode()+doc.getPhone()+"     Email : "+doc.getEmail(), fonts);
		SubPara2.setAlignment(Element.ALIGN_CENTER);
		document.add(SubPara2);

		document.add(new Chunk("\n"));
//        Chunk line = new Chunk(new LineSeparator());
//        document.add(line);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		float[] colWidths = {60f,40f};
		table.setWidths(colWidths);

		PdfPCell leftCell = new PdfPCell();
		leftCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);

		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 12);
		Paragraph doctoro = new Paragraph("PATIENT NAME :" + patiento.getTitle()+patiento.getFullName(), font2);
		doctoro.setAlignment(Element.ALIGN_LEFT);
		leftCell.addElement(doctoro);

		Font font4 = FontFactory.getFont(FontFactory.HELVETICA, 12);
		Paragraph phone = new Paragraph("ADDRESS :" + patiento.getAddress(), font4);
		phone.setAlignment(Element.ALIGN_LEFT);
		leftCell.addElement(phone);

		PdfPCell rightCell = new PdfPCell();
		rightCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);

		Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 11);
		Paragraph date = new Paragraph("DATE :" + receipt.getDate(), font3);
		date.setAlignment(Element.ALIGN_RIGHT);
		rightCell.addElement(date);

		Font font5 = FontFactory.getFont(FontFactory.HELVETICA, 11);
		Paragraph email = new Paragraph("AGE/SEX : " + patiento.getGender()+","+patiento.getAge()+" years", font5);
		email.setAlignment(Element.ALIGN_RIGHT);
		rightCell.addElement(email);

		table.addCell(leftCell);
		table.addCell(rightCell);

		document.add(table);

		String fontPath1 = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
		BaseFont customFont1 = BaseFont.createFont(fontPath1, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);


		// Add table headers
		Font dataFont1 = new Font(customFont1, 12);

//			    Font font6 = FontFactory.getFont(FontFactory.HELVETICA, 12);

		document.add(new Chunk("\n"));
		document.add(new Chunk("\n"));
		document.add(new Chunk("\n"));


		PdfPTable table2 = new PdfPTable(2);
		table2.setWidthPercentage(100);
		table2.setPaddingTop(25.2f);


		// Add table headers
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
		addTableCell2(table2, "Services", headerFont);
		addTableCell2(table2, "charges (Rs.)", headerFont);

		String fontPath = "/font/NotoSansDevanagari-Regular.ttf";  // Replace with the actual path to your font file
		BaseFont customFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, null, null);


		// Add table headers
		Font dataFont = new Font(customFont, 12);
		String medicine = receipt.getServices();
		String[] medics = medicine.split("\\|\\|");
		for (String medico : medics) {

			String[] medioData = medico.split(",,");
			for(String md : medioData) {

				addTableCell2(table2, md, dataFont);

			}

		}

		String total[] = {"SGST"," ","CGST"," ","Total"," "};

		for(String tot : total) {

			addTableCell2(table2,tot,dataFont);
		}


		document.add(table2);

		document.add(new Chunk("\n"));
		document.add(new Chunk("\n"));

		Paragraph signature = new Paragraph("Doctor Name And Signature", dataFont1);
		signature.setAlignment(Element.ALIGN_RIGHT);
		document.add(signature);

		document.add(new Chunk("\n"));
		byte[] imageUrl = doc.getSign();
		Image image = Image.getInstance(imageUrl);
		image.scaleAbsolute(100.0f, 55.0f);
		image.setAlignment(Element.ALIGN_RIGHT);
		document.add(image);

		Paragraph docName = new Paragraph(doc.getTitle()+" "+doc.getFullName(), dataFont1);
		docName.setAlignment(Element.ALIGN_RIGHT);
		document.add(docName);

		Paragraph degree = new Paragraph(doc.getQualification(), dataFont1);
		degree.setAlignment(Element.ALIGN_RIGHT);
		document.add(degree);




		document.close();


		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());

		HttpHeaders httpheaders = new HttpHeaders();
		httpheaders.add("Content-Disposition", "inline:file=prescript.pdf");

		return ResponseEntity
				.ok()
				.headers(httpheaders)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(byteArrayInputStream));
    	
    }
	private void addTableCell2(PdfPTable table2, String string, Font headerFont) {
		PdfPCell cell = new PdfPCell(new Phrase(string,headerFont));
		cell.setPadding(10.0f);
		table2.addCell(cell);

	}
    
    @GetMapping("/references/download/{id}")
    @ResponseBody
    public void downloadNote(@PathVariable("id") long id,HttpServletResponse response,Principal principal) throws IOException {
    	
    	String username = principal.getName();
    	Users user = us.getByUsername(username);
    	
    	Referral refer = us.getReferById(id);
    	
    	String patient = refer.getUsername();
    	
    	Users patientUser = us.getByUsername(patient);
    	
    	 PDDocument document = new PDDocument();

         // Create a new page
         PDPage page = new PDPage(PDRectangle.A4);
         document.addPage(page);

         // Create a new content stream for the page
         PDPageContentStream contentStream = new PDPageContentStream(document, page);

         // Set font and font size for the document
         contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

         // Define the document dimensions and positions
         float margin = 50;
         float yStart = page.getMediaBox().getHeight() - margin;
         float yPosition = yStart;

         // Title
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
         contentStream.showText("Hospital Referral Note");
         contentStream.endText();
         yPosition -= 30;

         // Referral Details
         contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText(refer.getDoctor());
         contentStream.endText();
         yPosition -= 20;
         
         contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText(refer.getHospital());
         contentStream.endText();
         yPosition -= 20;

         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("contact : "+user.getPhone());
         contentStream.endText();
         yPosition -= 20;
         yPosition -= 20;
         yPosition -= 20;

         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("Patient Information : ");
         contentStream.endText();
         yPosition -= 20;
         
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("Name : "+refer.getPatientName());
         contentStream.endText();
         yPosition -= 20;
         
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("contact : "+patientUser.getPhone());
         contentStream.endText();
         yPosition -= 20;
         yPosition -= 20;

         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("Date: " + refer.getDate());
         contentStream.endText();
         yPosition -= 30;
         yPosition -= 30;
         
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("Subject: " + refer.getSubject());
         contentStream.endText();
         yPosition -= 30;

         // Reason for Referral
         contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("Dear "+refer.getRecipientName());
         contentStream.endText();
         yPosition -= 20;

         contentStream.setFont(PDType1Font.HELVETICA, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("I am writing to refer my patient, "+refer.getPatientName()+", for further evaluation and treatment.");
         contentStream.endText();
         yPosition -= 20;
         
         contentStream.setFont(PDType1Font.HELVETICA, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("I have carefully reviewed the patient's medical history and current condition, ");
         contentStream.endText();
         yPosition -= 20;
         
         contentStream.setFont(PDType1Font.HELVETICA, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("and I believe that [he/she] would benefit from your expertise in ______________________.");
         contentStream.endText();
         yPosition -= 20;
         
         contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("Patient's Condition/Diagnosis:");
         contentStream.endText();
         yPosition -= 20;
         
         contentStream.setFont(PDType1Font.HELVETICA, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("_________________________________________________");
         contentStream.endText();
         yPosition -= 20;
         
         contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("Reason for Referral:");
         contentStream.endText();
         yPosition -= 20;
         
         
         contentStream.setFont(PDType1Font.HELVETICA, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("____________________________________________________");
         contentStream.endText();
         yPosition -= 20;
         yPosition -= 20;
         yPosition -= 20;
         
         
         contentStream.setFont(PDType1Font.HELVETICA, 12);
         contentStream.beginText();
         contentStream.newLineAtOffset(margin, yPosition);
         contentStream.showText("Thank you for your attention to this matter. I greatly appreciate your expertise and");
         contentStream.newLineAtOffset(0, -20);
         contentStream.showText("assistance in providing the best possible care for my patient. I will eagerly await your ");
         contentStream.newLineAtOffset(0, -20);
         contentStream.showText("evaluation and recommendations.");
         contentStream.newLineAtOffset(0, -20);
         contentStream.newLineAtOffset(0, -20);
         contentStream.showText("Sincerely,");
         contentStream.newLineAtOffset(0, -20);
         contentStream.newLineAtOffset(0, -20);
         contentStream.showText(refer.getDoctor());
         contentStream.newLineAtOffset(0, -20);
         contentStream.showText(refer.getHospital());
         contentStream.endText();
         
         
         // End the content stream
         contentStream.close();
         
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
 	    document.save(byteArrayOutputStream);
 	    document.close();

 	    response.setContentType("application/pdf");
 	    response.setHeader("Content-Disposition", "attachment; filename=ReferNote.pdf");

 	    response.getOutputStream().write(byteArrayOutputStream.toByteArray());
 	    response.getOutputStream().flush();

    	
    }
	

}
