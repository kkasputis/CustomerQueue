package cq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cq.entity.QueueTicket;
import cq.entity.Specialist;
import cq.repository.SpecialistRepository;
import cq.repository.TicketRepositoryImpl;

@Service
public class TicketService {

	@Autowired
	TicketRepositoryImpl ticketRepository;
	@Autowired
	SpecialistRepository specialistRepository;
	@Autowired
	SpecialistService specialistService;
	@Autowired
	MailService mailService;

	public int saveTicket(String specialistName, String email) {
		QueueTicket ticket = new QueueTicket();
		QueueTicket lastTicket = ticketRepository.findFirstByOrderByTicketCodeDesc().orElse(null);
		QueueTicket firstTicket = ticketRepository.findFirstByOrderByTicketCode().orElse(null);
		Specialist specialist = specialistRepository.findByUserName(specialistName).get();
		ticket.setSpecialist(specialist);
		ticket.setTime(specialistService.findNearestFreeTime(specialist));
		if (lastTicket == null) {
			ticket.setTicketCode(1);
		}
		else if((lastTicket.getTicketCode() > 999) && (firstTicket.getTicketCode() < 200)) {
		ticket.setTicketCode(1);
		}
		else {
			ticket.setTicketCode(lastTicket.getTicketCode() + 1);
		}
		ticket.setEmail(email);
		ticket.setActive(false);
		ticketRepository.save(ticket);
		mailService.sendTicketConfirmation(ticket);
		return ticket.getTicketCode();
	}
}
