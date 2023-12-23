package com.example.demo.controller;

import com.example.demo.entity.Certificates;
import com.example.demo.entity.Prescriptions;
import com.example.demo.entity.Receipt;
import com.example.demo.entity.Users;
import com.example.demo.service.usersService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;

import static java.util.Base64.getEncoder;

@Controller
public class PdfController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private usersService us;

    @GetMapping("/admin/all/prescriptions/download/{id}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> preDownload(HttpServletResponse response, @PathVariable("id") long id, Principal principal) throws IOException, DocumentException, DocumentException {

        Prescriptions prescript = us.getById(id);

        String doctor = principal.getName();
        Users doc = us.getByUsername(doctor);

        String patient = prescript.getUsername();
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

    @GetMapping("/admin/all/cerificates/download/{id}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> download(HttpServletResponse response,@PathVariable("id") long id) throws DocumentException, IOException {

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

    @GetMapping("/admin/all/receipts/download{id}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadReceipt(@PathVariable("id") long id,HttpServletResponse response) throws IOException, DocumentException {

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

    @GetMapping("/admin/all/prescriptions/SendEmail/{id}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> sendEmail(HttpServletResponse response, @PathVariable("id") long id, Principal principal) throws IOException, DocumentException, MessagingException {

        Prescriptions prescript = us.getById(id);

        String doctor = principal.getName();
        Users doc = us.getByUsername(doctor);

        String patient = prescript.getUsername();
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


        // Add table headers
        Font dataFont1 = new Font(customFont1, 10);

        Font font6 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Paragraph patientum = new Paragraph("PRESENT COMPLAINT: " , font6);
        patientum.setAlignment(Element.ALIGN_LEFT);
        document.add(patientum);

        Font font9 = FontFactory.getFont(FontFactory.HELVETICA, 10);
        String complaints = prescript.getComplaint();
        String[] splitted = complaints.split(",");
        for(String s : splitted ) {

            Paragraph complaint = new Paragraph(s.replace("||"," FROM LAST "), font9);
            complaint.setAlignment(Element.ALIGN_LEFT);
            document.add(complaint);
        }

        Font font7 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Paragraph patientAddress = new Paragraph("OBSERVATIONS: ", font7);
        patientAddress.setAlignment(Element.ALIGN_LEFT);
        document.add(patientAddress);

        Font font10 = FontFactory.getFont(FontFactory.HELVETICA, 10);
        String observations = prescript.getObservations();
        String[] SplittedObservations = observations.split(",");
        for(String s1 : SplittedObservations) {
            Paragraph observation = new Paragraph(s1.replace("||"," : "), font10);
            observation.setAlignment(Element.ALIGN_LEFT);
            document.add(observation);
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

//		    ByteArrayInputStream bis = new ByteArrayInputStream();

        // Create the email attachment
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(patiento.getEmail());
        helper.setSubject("Prescription");
        helper.setText("Hi, Good day,Prescription is attached with this email. Thank You for connecting with us. Visit Again!");

        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

        // Add the attachment
        helper.addAttachment("prescription.pdf", resource);

        // Send the email
        javaMailSender.send(message);

        // Set the content type and headers for the HTTP response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=prescription.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Return the response with the PDF file
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
    }

    @GetMapping("/admin/all/Prescriptions/sendOnWhatsapp/{id}")
    @ResponseBody
    public void sendOnWhatsapp(HttpServletResponse response, @PathVariable("id") long id, Principal principal) throws IOException, DocumentException, MessagingException, URISyntaxException {


        Prescriptions prescript = us.getById(id);

        String doctor = principal.getName();
        Users doc = us.getByUsername(doctor);

        String patient = prescript.getUsername();
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

        ArrayList<URI> medialUrls = new ArrayList<>();

        byte[] pdfBytes = out.toByteArray();

        File tempPdfFile = File.createTempFile("prescription", ".pdf");
        FileOutputStream fileOutputStream = new FileOutputStream(tempPdfFile);
        fileOutputStream.write(pdfBytes);
        fileOutputStream.close();

        PDDocument pdfDocument = PDDocument.load(tempPdfFile);
        PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
        BufferedImage images = pdfRenderer.renderImageWithDPI(0, 300);
        File tempImageFile = File.createTempFile("prescription", ".png");
        ImageIO.write(images, "png", tempImageFile);

        String mediaUrl = "data:application/pdf;base64",Base64;getEncoder().encodeToString(pdfBytes);

        tempPdfFile.toURI();

        medialUrls.add(tempImageFile.toURI());

        String ACCOUNT_SID = "ACe2ffc0e7ad24cb03314f3322bae7c0c5";
        String AUTH_TOKEN = "6d2b958052b3e0614f47b302bed25690";
        String FROM_PHONE_NUMBER = "whatsapp:+14155238886";
        String To_MOBILE_NUMBER = patiento.getCountrycode()+patiento.getPhone();
	        String messageBody = "Hello, this is a WhatsApp message sent using Twilio in Spring Boot!";

	        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	        Message message = Message.creator(new PhoneNumber("whatsapp:"+To_MOBILE_NUMBER), new PhoneNumber(FROM_PHONE_NUMBER),messageBody)
            .setMediaUrl(mediaUrl)
            .create();
	        System.out.println("Message SID: " + message.getSid());





    }
}
