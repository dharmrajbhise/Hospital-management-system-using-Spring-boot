package com.example.demo.entity;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String title;

	private String fullName;

	private String username;

	private String password;

	private String gender;

	private String email;

	private String bloodGroup;

	private String address;

	private String countrycode;

	private String phone;

	private String birth_date;

	private String age;

	private String hospital;

	private String qualification;

	@Lob
	private byte[] sign;

	@Lob
	private byte[] logo;

	@Lob
	@Column(columnDefinition="LongBlob")
	private byte[] profilephoto;

	@Column(columnDefinition="LONGTEXT")
	private String base64Image;

	@Column(name = "Added_By")
	private String addedby;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;

	public Users() {}

	public Users(String title,String fullName, String username, String password, String gender, String email,String bloodGroup ,String address,String countrycode,String phone,
				 String birth_date,String age,String hospital,String qualification ,Role role,byte[] sign,byte[] logo,byte[] profilephoto,String base64Image,String addedby) {
		super();
		this.title = title;
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.email = email;
		this.bloodGroup = bloodGroup;
		this.address = address;
		this.countrycode =countrycode;
		this.phone = phone;
		this.birth_date = birth_date;
		this.age = age;
		this.hospital = hospital;
		this.qualification = qualification;
		this.role = role;
		this.sign = sign;
		this.logo = logo;
		this.profilephoto = profilephoto;
		this.base64Image = base64Image;
		this.addedby = addedby;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}



	public byte[] getSign() {
		return sign;
	}

	public void setSign(byte[] sign) {
		this.sign = sign;
	}


	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public byte[] getProfilephoto() {
		return profilephoto;
	}

	public void setProfilephoto(byte[] profilephoto) {
		this.profilephoto = profilephoto;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	public String getAddedby() {
		return addedby;
	}

	public void setAddedby(String addedby) {
		this.addedby = addedby;
	}

	@Override
	public String toString() {
		return "Users{" +
				"id=" + id +
				", title='" + title + '\'' +
				", fullName='" + fullName + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", gender='" + gender + '\'' +
				", email='" + email + '\'' +
				", bloodGroup='" + bloodGroup + '\'' +
				", address='" + address + '\'' +
				", countrycode='" + countrycode + '\'' +
				", phone='" + phone + '\'' +
				", birth_date='" + birth_date + '\'' +
				", age='" + age + '\'' +
				", hospital='" + hospital + '\'' +
				", qualification='" + qualification + '\'' +
				", sign=" + Arrays.toString(sign) +
				", logo=" + Arrays.toString(logo) +
				", profilephoto=" + Arrays.toString(profilephoto) +
				", base64Image='" + base64Image + '\'' +
				", addedby='" + addedby + '\'' +
				", role=" + role +
				'}';
	}
}
