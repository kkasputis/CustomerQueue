package cq.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cq.entity.QueueTicket;
import cq.entity.Specialist;
import cq.repository.SpecialistRepository;
import cq.repository.TicketRepositoryImpl;
import cq.service.MailService;

@Controller
@RequestMapping("/specialist")
public class SpecialistController {
	
	@Autowired
	SpecialistRepository specialistRepository;
	@Autowired
	TicketRepositoryImpl ticketRepository;
	@Autowired
	MailService mailService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		Specialist specialist = specialistRepository.findByUserName(request.getUserPrincipal().getName()).get();
		List<QueueTicket> ticketList = specialist.getTickets();
		ticketList.sort((o1,o2) -> o1.getTime().compareTo(o2.getTime()));
				model.addAttribute("ticketList", ticketList);
		return "management";
	}
	@RequestMapping(path="/activate", method = RequestMethod.GET)
	@ResponseBody
	public boolean startMeeting(Model model, HttpServletRequest request, @RequestParam(name = "code")int code) {
		
		Specialist specialist = specialistRepository.findByUserName(request.getUserPrincipal().getName()).get();
		boolean active = ticketRepository.existsBySpecialistAndActive(specialist, true);
		if (active == false) {
		QueueTicket ticket = ticketRepository.findByTicketCode(code).orElse(null);
		if (ticket != null) {
		ticket.setActive(true);
		ticketRepository.save(ticket);
		mailService.appointmentStart(ticket);
		return true;
		}
		}
		 return false;
	}
	@RequestMapping(path="/endmeeting", method = RequestMethod.GET)
	@ResponseBody
	public boolean endMeeting(Model model, HttpServletRequest request) {
		
		Specialist specialist = specialistRepository.findByUserName(request.getUserPrincipal().getName()).get();
		QueueTicket ticket = ticketRepository.findFirstBySpecialistAndActive(specialist, true).orElse(null);
		if (ticket != null) {
		ticketRepository.delete(ticket);
		return true;
		}
		
		 return false;
	}
	
	@RequestMapping(path="/cancel", method = RequestMethod.GET)
	@ResponseBody
	public boolean cancelMeeting(Model model, HttpServletRequest request, @RequestParam(name = "code")int code) {
		Specialist specialist = specialistRepository.findByUserName(request.getUserPrincipal().getName()).get();
		QueueTicket ticket = ticketRepository.findByTicketCode(code).orElse(null);

		if ((ticket != null) && (ticket.getSpecialist() == specialist)) {
			ticketRepository.delete(ticket);
		mailService.appointmentCanceled(ticket);
		return true;
		}
		
		 return false;
	}
}
