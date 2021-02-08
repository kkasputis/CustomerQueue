package cq.service;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cq.entity.QueueTicket;


@Service
public class MailService {
	private final String SERVICE_MAIL = "Info <info@katinukatalogas.lt>";
	@Autowired
	private JavaMailSender mailSender;

	@Async
	public void sendTicketConfirmation(QueueTicket ticket) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		mailMessage.setTo(ticket.getEmail());
		mailMessage.setSubject("Confirmation");
		mailMessage.setFrom(SERVICE_MAIL);
		mailMessage.setText("Your appointment with " + ticket.getSpecialist().getName() + " has been scheduled for "
				+ ticket.getTime().format(formatter) + ". \n Your ticked number is: " + ticket.getTicketCode());
		mailSender.send(mailMessage);
	}
	@Async
	public void appointmentStart(QueueTicket ticket) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(ticket.getEmail());
		mailMessage.setSubject("Appointment Starts NOW");
		mailMessage.setFrom(SERVICE_MAIL);
		mailMessage.setText("Your appointment with " + ticket.getSpecialist().getName() + " starts now. Go to the specialist.");
		mailSender.send(mailMessage);
	}
	
	@Async
	public void appointmentCanceled(QueueTicket ticket) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(ticket.getEmail());
		mailMessage.setSubject("Appointment Canceled");
		mailMessage.setFrom(SERVICE_MAIL);
		mailMessage.setText("Unfortunately, your appointment with " + ticket.getSpecialist().getName() + " ticket code: " + ticket.getTicketCode() 
		+ " has been canceled. Try registering a new ticket.");
		mailSender.send(mailMessage);
	}
	@Async
	public void sendNewPassword(String email, String newPassword) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(email);
		mailMessage.setSubject("New password");
		mailMessage.setFrom(SERVICE_MAIL);
		mailMessage.setText("Your password has been changed. \n "
						+ "Your new password: " + newPassword);
		mailSender.send(mailMessage);

	}
}
