package com.example.demo.controller;


import com.example.demo.entity.*;
import com.example.demo.repo.RoleRepo;
import com.example.demo.service.usersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Controller
public class HomeController {
	
	@Autowired
	private RoleRepo repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private usersService us;
	
	@GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/user")
    public String secured(Principal principal, Model model,Role role) {
    	
    	String username = principal.getName();
    	
    	Users user = us.getByUsername(username);
    	if (user != null) {
    	    System.out.println(user.getFullName());
    	} else {
    	    System.out.println("User is null");
    	}
    	model.addAttribute("user", user);
//    	System.out.println(user.getFullName());
    	
    	model.addAttribute("username", username);
    	
    	List<Role> user2 = us.getByname("DOCTOR");
    	
    	List<Users> ids = new ArrayList<>();
    	 for(Role u1 : user2) {
    		 	
    		 long id = u1.getId();
    		 List<Users> user3 = us.findByRoleId(id);
    		 ids.addAll(user3);
    	}	 
    	 
    	 model.addAttribute("usern", ids);
    	 
    	
        return "user";
    }
    
    @GetMapping("/about")
    public String about() {
    	return "about";
    }
    
    @GetMapping("/contact")
    public String contact() {
    	return "contact";
    }
    
    @PostMapping("/contact-message")
    public String message(@ModelAttribute Messages message,Model model) {
    	
    	us.saveMessage(message);
    	
    	model.addAttribute("message","Message sent successfully!");
    	
    	return "index";
    }
    
    
    @GetMapping("/login")
    public String login(HttpServletRequest request,@RequestParam(value = "error", required = false) String error, Model model) {

    	if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }
    	
    	 String url = request.getRequestURI();
         if (url.contains("/admin") && !url.contains("/user")) {
             return "redirect:/error";
         }
    	
    	return "login";
    }
    
    @GetMapping("/error")
    public String showErrorPage() {
        return "error";
    }
    
    
    @GetMapping("/register")
    public String register() {
    	return "register";
    }
    
    @PostMapping("/Registration")
    public String registration(@ModelAttribute Users user,Model model) {
    	
    	Role role = repo.save(user.getRole());
    	user.setRole(role);
    	
    	String password = passwordEncoder.encode(user.getPassword());
    	user.setPassword(password);
    	user.setAddedby("self");
    	us.saveUser(user);
    	
    	model.addAttribute("message","User Registered Successfully!");
    	
    	
    	return "register";
    }
    
    @RequestMapping("/admin")
	public String admin(Model model,Principal principal) {
    	
    	String username = principal.getName();
    	
    	Users user = us.getByUsername(username);
    	
    	String doctor = user.getFullName();
    	
    	List<Appoinments> appoint = us.getByDoctor(doctor);
    	
    	model.addAttribute("appoint", appoint);
		
		return "admin";
	}

	@GetMapping("/reception")
	public String recept(Model model,@RequestParam(defaultValue = "1") int page,Principal principal) {

		List<Users> users = us.getAllPatients();

		String recept = principal.getName();
		Users receptionist = us.getByUsername(recept);
		String added = receptionist.getAddedby();
		List<Users> SpUsers = us.GetByAddedBy(added);

		List<Users> selfAdded = us.GetByAddedBy("self");
		for(Users u2 : selfAdded){
			SpUsers.add(u2);
		}

		int totalPages = (int) Math.ceil((double) SpUsers.size() / 5);

		int start = (page - 1) * 5;
		int end = Math.min(start + 5, SpUsers.size());
		List<Users> currentPageProducts = SpUsers.subList(start, end);

		model.addAttribute("products", currentPageProducts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);

//		System.out.println(users);

		return "manage";
	}

	@GetMapping("/autocomplete")
	@ResponseBody
	public List<String> autocomplete(@RequestParam String term,Model model) {

		List<Users> users = us.getAllPatients();
		model.addAttribute("users", users);

		List<String> data = new ArrayList<>();

		for(Users full : users) {

			String fullNames = full.getFullName();
			data.add(fullNames);
		}

//		Users user = us.getByFullName(fullName);
//		model.addAttribute("user", user);

		List<String> suggestions = new ArrayList<>();
		for (String item : data) {
			if (item != null &&item.toLowerCase().startsWith(term.toLowerCase())) {
				suggestions.add(item);
			}
		}
		return suggestions;
	}

	@GetMapping("/reception/addPatient")
	public String addpatient(Model model) {

		Random random = new Random();
		int randomNumber = random.nextInt(100000);
		model.addAttribute("password",randomNumber);
		return "recept";
	}

	@PostMapping("/reception/addPatient")
	public String patientadded(@ModelAttribute Users user,Model model,@RequestParam("password") String password,@RequestParam("countrycode") String countrycode,@RequestParam("phone") String phone,@RequestParam("username") String username,@RequestParam("fullName") String fullName,Principal principal) {

		Random random = new Random();
		int randomNumber = random.nextInt(10000);

		String generated = fullName.replace(" ", "")+randomNumber;

		user.setUsername(fullName.replace(" ", "")+randomNumber);

			Role role = repo.save(user.getRole());
			user.setRole(role);

			String password2 = passwordEncoder.encode(user.getPassword());
			user.setPassword(password2);

			String reception = principal.getName();
			Users resp = us.getByUsername(reception);
			String respName = resp.getAddedby();
			user.setAddedby(respName);

			us.saveUser(user);

		String ACCOUNT_SID = "ACe2ffc0e7ad24cb03314f3322bae7c0c5";
		String AUTH_TOKEN = "6d2b958052b3e0614f47b302bed25690";
		String FROM_PHONE_NUMBER = "+12282313744";

		com.twilio.Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		com.twilio.rest.api.v2010.account.Message twilioMessage = com.twilio.rest.api.v2010.account.Message.creator(
				ACCOUNT_SID,new com.twilio.type.PhoneNumber("+"+countrycode+phone),
				new com.twilio.type.PhoneNumber(FROM_PHONE_NUMBER),
				"You are successfully registered ! your username is "+generated+" and temporary password is "+password+" Thanks for visiting!").create();

		System.out.println("SMS sent successfully " + twilioMessage.getSid());

			model.addAttribute("message","Patient Added Successfully!");


		return "recept";
	}

	@PostMapping("/reception/searchByName")
	public String nameFilter(@RequestParam("fullName") String fullName,Model model) {

		Users user = us.getByFullName(fullName);
		model.addAttribute("user", user);

		List<Users> users = new ArrayList<>();
		if (user != null) {
			users.add(user);
		}

		model.addAttribute("users", users);

		return "manage";
	}

	@GetMapping("/medical")
	public String medical(Model model) {

		LocalDate currentDate = LocalDate.now();

		model.addAttribute("date", currentDate);

		List<Prescriptions> prescript = us.getAllByDate(currentDate);
		model.addAttribute("prescript", prescript);

		return "Medical";
	}

	@PostMapping("/sendMessageToAdmin")
	public String sendMessage(Model model,@ModelAttribute Doctors doctor,@RequestParam("mobile") String mobile){

		us.saveDocor(doctor);
		String ACCOUNT_SID = "ACe2ffc0e7ad24cb03314f3322bae7c0c5";
		String AUTH_TOKEN = "6d2b958052b3e0614f47b302bed25690";
		String FROM_PHONE_NUMBER = "+12282313744";

		com.twilio.Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		com.twilio.rest.api.v2010.account.Message twilioMessage = com.twilio.rest.api.v2010.account.Message.creator(
				ACCOUNT_SID,new com.twilio.type.PhoneNumber("+91"+mobile),
				new com.twilio.type.PhoneNumber(FROM_PHONE_NUMBER),
				"Hi Welcome to Sunshine HealthCare Solutions please Join our whatsapp group for conversation Group Join link: https://chat.whatsapp.com/EwawhNbrQ6V1VITwQf1ekn").create();

		System.out.println("SMS sent successfully " + twilioMessage.getSid());

		model.addAttribute("message","message sent successfully! check your mobile");
		return "index";
	}

	@GetMapping("/reception/addImage{id}")
	public String addPicture(Model model,@PathVariable("id") long id){

		model.addAttribute("id",id);

		return "camerabro";
	}

	@PostMapping("/reception/saveImage")
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

	@GetMapping("/logout")
	public String logout(HttpServletResponse response) {
		// Call the deleteCookie method here
		deleteCookie(response);
		// Perform any other logout actions, like clearing session data, etc.
		return "redirect:/"; // Redirect the user to the login page after logout
	}

	private void deleteCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("yourCookieName", null);
		cookie.setMaxAge(0);
		cookie.setPath("/"); // Make sure to set the same path used when creating the cookie
		response.addCookie(cookie);
	}
	
}
