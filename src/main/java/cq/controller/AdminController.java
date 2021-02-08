package cq.controller;

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
import cq.entity.Specialist;
import cq.repository.AppRoleRepository;
import cq.repository.SpecialistRepository;
import cq.repository.UserRoleRepository;
import cq.security.entity.UserRole;


@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	SpecialistRepository specialistRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	@Autowired
	AppRoleRepository appRoleRepository;

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		model.addAttribute("specialistList", specialistRepository.findAll());
		return "admin";
	}
	
	@RequestMapping(path="/newspecialist", method = RequestMethod.POST)
	public String registerSpecialist(Model model, @ModelAttribute("newSpecialist") Specialist specialist) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		specialist.setEncryptedPassword(encoder.encode(specialist.getEncryptedPassword()));
		specialist.setEnabled(1);
		specialistRepository.save(specialist);
		UserRole userRole = new UserRole();
		userRole.setAppUser(specialist);
		userRole.setAppRole(appRoleRepository.findById((long) 3).get());
		userRoleRepository.save(userRole);
	 return "redirect:/admin";

		}
	@RequestMapping(path="/newspecialist", method = RequestMethod.GET)
	public String registerUser(Model model, HttpServletRequest request) {
		model.addAttribute("newSpecialist", new Specialist());
		
	 return "new_specialist";

		}
	
	@RequestMapping(path="/removespecialist", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteSpecialist(Model model, @RequestParam(name = "id")long id) {
		Specialist specialist = specialistRepository.findById(id).get();
		List<UserRole> userRoles = userRoleRepository.findFirstByAppUser(specialist).orElse(null);
		userRoleRepository.deleteAll(userRoles);
		specialistRepository.delete(specialist);
		
	 return "removed";

		}
}

