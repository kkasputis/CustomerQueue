package cq.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cq.entity.Customer;
import cq.repository.CustomerRepository;
@Service
public class CustomerService {
@Autowired
CustomerRepository customerRepository;
@Autowired
MailService mailService;

	public void changePassword(Customer customer) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String newPassword = alphaNumericString(10);
		customer.setEncryptedPassword(encoder.encode(newPassword));
		customerRepository.save(customer);
		mailService.sendNewPassword(customer.getEmail(), newPassword);
		
	}
	String alphaNumericString(int len) {
	    String AB = "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
	    Random rnd = new Random();

	    StringBuilder sb = new StringBuilder(len);
	    for (int i = 0; i < len; i++) {
	        sb.append(AB.charAt(rnd.nextInt(AB.length())));
	    }
	    return sb.toString();
	}
}
