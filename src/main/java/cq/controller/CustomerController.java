package cq.controller;

import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cq.entity.Customer;
import cq.entity.QueueTicket;
import cq.repository.AppRoleRepository;
import cq.repository.AppUserRepository;
import cq.repository.CustomerRepository;
import cq.repository.SpecialistRepository;
import cq.repository.TicketRepositoryImpl;
import cq.repository.UserRoleRepository;
import cq.security.entity.AppUser;
import cq.security.entity.UserRole;
import cq.service.CustomerService;
import cq.service.TicketService;

@Controller
public class CustomerController {
	@Autowired
	SpecialistRepository specialistRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	@Autowired
	AppRoleRepository appRoleRepository;
	@Autowired
	TicketRepositoryImpl ticketRepository;
	@Autowired
	TicketService ticketService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AppUserRepository appUserRepository;
	@Autowired
	CustomerService customerService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		return "index";
	}

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login(Model model) {

		return "login";
	}

	@RequestMapping(path = "/newticket", method = RequestMethod.GET)
	public String newTicket(Model model) {
		model.addAttribute("specialistList", specialistRepository.findAll());
		return "new_ticket";
	}

	@RequestMapping(path = "/newuser", method = RequestMethod.GET)
	public String newUser(Model model) {
		model.addAttribute("newCustomer", new Customer());
		return "new_user";
	}

	@RequestMapping(path = "/newuser", method = RequestMethod.POST)
	public String saveNewUser(Model model, @ModelAttribute("newCustomer") Customer customer) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		customer.setEncryptedPassword(encoder.encode(customer.getEncryptedPassword()));
		customer.setEnabled(1);
		customerRepository.save(customer);
		UserRole userRole = new UserRole();
		userRole.setAppUser(customer);
		userRole.setAppRole(appRoleRepository.findById((long) 2).get());
		userRoleRepository.save(userRole);
		return "redirect:/login";
	}

	@RequestMapping(path = "/newticket", method = RequestMethod.POST)
	public String newTicketPost(Model model, HttpServletRequest request, @RequestParam String specialist,
			@RequestParam(value = "email", required = false) String email) {

		int ticketCode = 0;
		if ((email == null) && (request.isUserInRole("ROLE_USER"))) {
			email = customerRepository.findByUserName(request.getUserPrincipal().getName()).get().getEmail();
		}
		if (email != null) {
			ticketCode = ticketService.saveTicket(specialist, email);
		}
		if (ticketCode > 0) {
			return "redirect:/ticket?code=" + ticketCode;
		} else
			return "redirect/ticket?error=true";
	}

	@RequestMapping(path = "/ticket", method = RequestMethod.GET)
	public String ticketInfo(Model model, @RequestParam(value = "error", defaultValue = "false") boolean error,
			@RequestParam(value = "code", required = false) String ticketCodeString) {
		if ((ticketCodeString != null) && (ticketCodeString.matches("-?\\d+"))) {
			int ticketCode = Integer.valueOf(ticketCodeString);
			QueueTicket ticket = ticketRepository.findByTicketCode(ticketCode).orElse(null);
			if (ticket != null) {
				model.addAttribute("ticket", ticket);
			}
			else {
				model.addAttribute("badCode", ticketCodeString); }
		} 
		else {
			model.addAttribute("badCode", ticketCodeString);
		}
		if (error) {
			model.addAttribute("errorMessage", "Could not reserve ticket. Please, try again.");
		}
		return "ticket";
	}

	@RequestMapping(path = "/displayboard", method = RequestMethod.GET)
	public String displayBoard(Model model) {

		return "displayboard";
	}

	@RequestMapping(path = "/profile", method = RequestMethod.GET)
	public String profile(Model model, HttpServletRequest request) {
		Customer customer = customerRepository.findByUserName(request.getUserPrincipal().getName()).orElse(null);
		if (customer != null) {
			List<QueueTicket> tickets = ticketRepository.findByEmail(customer.getEmail()).orElse(null);
			tickets.sort(Comparator.comparing(QueueTicket::getTime));
			model.addAttribute("tickets", tickets);
		}
		return "profile";
	}

	@RequestMapping(path = "/displayboard/board", method = RequestMethod.GET)
	public String board(Model model) {

		model.addAttribute("activeTickets", ticketRepository.findByActive(true).orElse(null));
		model.addAttribute("nextTickets", ticketRepository.findTop5ByActiveOrderByTime(false).orElse(null));

		return "board";
	}

	@RequestMapping(path = "/cancelticket", method = RequestMethod.GET)
	public String cancelTicket(Model model, HttpServletRequest request, @RequestParam(value = "code") int code,
			@RequestParam(value = "email", required = false) String email) {
		QueueTicket ticket = ticketRepository.findByTicketCode(code).orElse(null);
		if ((email == null) && (request.getUserPrincipal() != null)) {
			Customer customer = customerRepository.findByUserName(request.getUserPrincipal().getName()).orElse(null);

			if ((customer != null) && (ticket != null) && (customer.getEmail().equals(ticket.getEmail()))) {
				ticketRepository.delete(ticket);
			}
		} else if ((email != null) && (ticket.getEmail().equalsIgnoreCase(email))) {
			ticketRepository.delete(ticket);
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String registerUser(Model model, @ModelAttribute("oldPass") String oldPass,
			@ModelAttribute("newPass") String newPass, HttpServletRequest request) {
		if (request.getUserPrincipal() != null) {

			AppUser user = appUserRepository.findByUserName(request.getUserPrincipal().getName()).get();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(oldPass, user.getEncryptedPassword())) {
				user.setEncryptedPassword(encoder.encode(newPass));
				appUserRepository.save(user);
			}
		}
		return "redirect:/login";
	}

	@RequestMapping(path = "/checkpassword", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkPassword(Model model, HttpServletRequest request, @ModelAttribute("oldPass") String oldPass) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		AppUser user = appUserRepository.findByUserName(request.getUserPrincipal().getName()).get();
		return encoder.matches(oldPass, user.getEncryptedPassword());
	}

	@RequestMapping(path = "/ticket/checkactive", method = RequestMethod.GET)
	@ResponseBody
	public boolean ticketActivation(Model model, @RequestParam(value = "code") int ticketCode) {
		QueueTicket ticket = ticketRepository.findByTicketCode(ticketCode).orElse(null);
		if (ticket != null) {
			return ticket.isActive();
		}
		return false;
	}

	@RequestMapping(path = "/newuser/username", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkUserName(Model model, @RequestParam(value = "username") String userName) {
		return appUserRepository.existsByUserName(userName);
	}

	@RequestMapping(path = "/newuser/email", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkEmail(Model model, @RequestParam(value = "email") String email) {
		return customerRepository.existsByEmail(email);
	}

	@RequestMapping(path = "/changepassword", method = RequestMethod.GET)
	@ResponseBody
	public boolean changePassword(Model model, @RequestParam(value = "username") String username) {
		Customer customer = customerRepository.findByUserName(username).orElse(null);
		if ((customer != null) && (customer.getEmail() != null)) {
			customerService.changePassword(customer);
		}
		return true;
	}
}
