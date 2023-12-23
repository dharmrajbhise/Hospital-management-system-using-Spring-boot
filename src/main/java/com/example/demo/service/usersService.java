package com.example.demo.service;

import com.example.demo.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface usersService {
	Users saveUser(Users user);

	Page<Users> getAllUsers(Pageable pageable);

	List<Users> getAllPatients();

	List<Users> getAllUsers();

	List<Users> getByEmail(String email);

	List<Users> getByBlood(String bloodGroup);

	Role saveRole(Users user);

	Messages saveMessage(Messages message);

	List<Messages> getAll();

	Users getByUsername(String username);

	Users getByFullName(String fullName);

	Appoinments saveAppoinment(Appoinments appoint);

	List<Appoinments> getByName(String name );

	List<Appoinments> getall();

	List<Appoinments> getByDate(String date);

	List<Prescriptions> getAllPrescript();

	Prescriptions savePriscriptions(Prescriptions prescript);

	Certificates saveCertificate(Certificates certify);

	List<Certificates> getAllCertificatesByUsername(String username);

	Certificates getcertById(long id);

	Prescriptions getById(long id);

	List<Role> getByname(String name);

	List<Users> findByRoleId(Long id);

	List<lab_report> getAllReportsByUsername(String username);

	lab_report saveReport(MultipartFile file,String username,String type) throws IOException;

	lab_report getReportById(long id);

	lab_report loadReport(String fileName) throws FileNotFoundException;

	List<Receipt> getAllReceipts();

	Receipt saveReceipt(Receipt receipt);

	Receipt getByReceiptId(long id);

	List<Referral> getAllReferrals();

	Referral saveReferral(Referral refer);

	Referral getReferById(long id);

	List<Certificates> getCertByUsername(String username);

	List<Prescriptions> getPresByUsername(String username);

	List<lab_report> getReportByUsername(String username);

	List<Receipt> getReceiptByUsername(String username);

	List<Referral> getReferralByUsername(String username);

	List<Appoinments> getByDoctor(String doctor);

	Medicines addMedicines(Medicines medicine);

	List<Medicines> getMediByUsername(String username);

	Medicines UpdateMedicine(long id,Medicines medi);

	List<Prescriptions> getAllByDate(LocalDate date);

	boolean isUserExist(String username);

	Users geUserById(long id);

	List<Users> GetByAddedBy(String addedby);

	Doctors saveDocor(Doctors doctor);

	HealthChart saveRecord(HealthChart record);

	List<HealthChart> getRecordByUsername(String username);
	

}
