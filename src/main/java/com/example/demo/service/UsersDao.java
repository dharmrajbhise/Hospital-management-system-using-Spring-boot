package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class UsersDao implements usersService {

	@Autowired
	private HealthRepo repo12;

	@Autowired
	private DctorsRepo repo11;

	@Autowired
	private MediRepo repo10;
	
	@Autowired
	private ReferralRepo repo9;
	
	@Autowired
	private ReceiptRepo repo8;
	
	@Autowired
	private ReportRepo repo7;
	
	@Autowired
	private CertifyRepo repo6;
	
	@Autowired
	private PrescriptRepo repo5;
	
	@Autowired
	private AppointRepo repo4;
	
	@Autowired
	private MessagesRepo repo3;
	
	@Autowired
	private RoleRepo repo2;
	
	@Autowired
	private UsersRepo repo;

	@Override
	public Users saveUser(Users user) {
		
		return repo.save(user);
	}

	@Override
	public Page<Users> getAllUsers(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public List<Users> getAllPatients() {
		return repo.findAll();
	}

	@Override
	public Role saveRole(Users user) {
		
		return repo2.save(user.getRole()) ;
	}

	@Override
	public Messages saveMessage(Messages message) {
		
		return repo3.save(message);
	}

	@Override
	public List<Messages> getAll() {
		
		return repo3.findAll();
	}

	@Override
	public Users getByUsername(String username) {
		
		return repo.findByUsername(username);
	}

	@Override
	public Appoinments saveAppoinment(Appoinments appoint) {
		
		return repo4.save(appoint);
	}

	@Override
	public List<Appoinments> getByName(String name) {
		
		return repo4.findByName(name);
	}

	@Override
	public List<Appoinments> getall() {
		
		return repo4.findAll();
	}

	@Override
	public List<Appoinments> getByDate(String date) {
		
		return repo4.findByDate(date);
	}

	@Override
	public List<Users> getAllUsers() {
		
		return repo.findAll();
	}

	@Override
	public List<Users> getByEmail(String email) {
		
		return repo.findByEmail(email);
	}

	@Override
	public List<Users> getByBlood(String bloodGroup) {
		
		return repo.findByBloodGroup(bloodGroup);
	}

	@Override
	public List<Prescriptions> getAllPrescript() {
		
		return repo5.findAll();
	}

	@Override
	public Prescriptions savePriscriptions(Prescriptions prescript) {
		
		return repo5.save(prescript);
	}

	@Override
	public Certificates saveCertificate(Certificates certify) {
		
		return repo6.save(certify);
	}

	@Override
	public List<Certificates> getAllCertificatesByUsername(String username) {
		
		return repo6.findByUsername(username);
	}

	@Override
	public Certificates getcertById(long id) {
		
		return repo6.findById(id).orElse(null);
	}

	@Override
	public Prescriptions getById(long id) {
		
		return repo5.findById(id).orElse(null);
	}

	@Override
	public List<Role> getByname(String name) {
		
		return repo2.findByName(name);
	}

	@Override
	public List<Users> findByRoleId(Long id) {
		
		return repo.findByRoleId(id);
	}

	@Override
	public List<lab_report> getAllReportsByUsername(String username) {
		return repo7.findByUsername(username);
	}

	@Override
	public Users getByFullName(String fullName) {
		
		return repo.findByFullName(fullName);
	}

//	@Override
//	public List<lab_report> getAllReports() {
//
//		return repo7.findAll();
//	}

	@Override
	public lab_report saveReport(MultipartFile file,String username,String type) throws IOException {
		
		String fileName = file.getOriginalFilename();
		byte[] files = file.getBytes();
        lab_report labReport = new lab_report();
			labReport.setFile(files);
			labReport.setUsername(username);
			labReport.setType(type);
			labReport.setFileName(fileName);
			return repo7.save(labReport);
			
		
	}

	@Override
	public lab_report getReportById(long id) {
		
		return repo7.findById(id).orElse(null);
	}

	@Override
	public lab_report loadReport(String fileName) throws FileNotFoundException {
		
		lab_report labReport = repo7.findByFileName(fileName);
        if (labReport == null) {
            throw new FileNotFoundException("Lab report not found: " + fileName);
        }

        // Load the file data from the lab report entity
        byte[] fileData = labReport.getFile();

        // Save the file data to a temporary file
        String tempFileName = "temp_" + fileName;
        Path tempFilePath = Paths.get(tempFileName);
        try {
			Files.write(tempFilePath, fileData);
			// Read the file content
			byte[] content = Files.readAllBytes(tempFilePath);
			
			// Delete the temporary file
			Files.delete(tempFilePath);
			labReport.setFile(content);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        // Update the lab report entity with the actual file content

        return labReport;
		
		
	}

	@Override
	public List<Receipt> getAllReceipts() {
		
		return repo8.findAll();
	}

	@Override
	public Receipt saveReceipt(Receipt receipt) {
		
		return repo8.save(receipt);
	}

	@Override
	public Receipt getByReceiptId(long id) {
		
		return repo8.findById(id).orElse(null);
	}

	@Override
	public List<Referral> getAllReferrals() {
		
		return repo9.findAll();
	}

	@Override
	public Referral saveReferral(Referral refer) {
		
		return repo9.save(refer);
	}

	@Override
	public Referral getReferById(long id) {
		
		return repo9.findById(id).orElse(null);
	}

	@Override
	public List<Certificates> getCertByUsername(String username) {

		return repo6.findByUsername(username);
	}

	@Override
	public List<Prescriptions> getPresByUsername(String username) {
		
		return repo5.findByUsername(username);
	}

	@Override
	public List<lab_report> getReportByUsername(String username) {
		
		return repo7.findByUsername(username);
	}

	@Override
	public List<Receipt> getReceiptByUsername(String username) {
		
		return repo8.findByUsername(username);
	}

	@Override
	public List<Referral> getReferralByUsername(String username) {
		
		return repo9.findByUsername(username);
	}

	@Override
	public List<Appoinments> getByDoctor(String doctor) {
		
		return repo4.findByDoctor(doctor);
	}

	@Override
	public Medicines addMedicines(Medicines medicine) {
		return repo10.save(medicine);
	}

	@Override
	public List<Medicines> getMediByUsername(String username) {
		return repo10.findByUsername(username);
	}

	@Override
	public Medicines UpdateMedicine(long id, Medicines medi) {
		Medicines existingMedicine = repo10.findById(id).orElse(null);


		existingMedicine.setMediname(medi.getMediname());
		existingMedicine.setStrength(medi.getStrength());
		existingMedicine.setDosage(medi.getDosage());
		existingMedicine.setDays(medi.getDays());
		existingMedicine.setUnit(medi.getUnit());
		existingMedicine.setRemark(medi.getRemark());

		Medicines updatedMedicine = repo10.save(existingMedicine);

		return updatedMedicine;
	}

	@Override
	public List<Prescriptions> getAllByDate(LocalDate date) {
		return repo5.findByDate(date);
	}

	@Override
	public boolean isUserExist(String username) {
		return false;
	}

	@Override
	public Users geUserById(long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public List<Users> GetByAddedBy(String addedby) {
		return repo.findByAddedby(addedby);
	}

	@Override
	public Doctors saveDocor(Doctors doctor) {
		return repo11.save(doctor);
	}

	@Override
	public HealthChart saveRecord(HealthChart record) {
		return repo12.save(record);
	}

	@Override
	public List<HealthChart> getRecordByUsername(String username) {
		return repo12.findByUsername(username);
	}


}
