package cq.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import cq.security.entity.AppUser;

@Entity
public class Specialist extends AppUser{
	private int usualServiceTime;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist")
	private List<QueueTicket> tickets;
	private String workHoursFrom;
	private String workHoursTo;
	private String name;
	
	public int getUsualServiceTime() {
		return usualServiceTime;
	}
	public void setUsualServiceTime(int usualServiceTime) {
		this.usualServiceTime = usualServiceTime;
	}
	public List<QueueTicket> getTickets() {
		return tickets;
	}
	public void setTickets(List<QueueTicket> tickets) {
		this.tickets = tickets;
	}
	public String getWorkHoursFrom() {
		return workHoursFrom;
	}
	public void setWorkHoursFrom(String workHoursFrom) {
		this.workHoursFrom = workHoursFrom;
	}
	public String getWorkHoursTo() {
		return workHoursTo;
	}
	public void setWorkHoursTo(String workHoursTo) {
		this.workHoursTo = workHoursTo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



	
}
