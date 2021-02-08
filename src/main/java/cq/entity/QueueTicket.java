package cq.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class QueueTicket {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
private int ticketCode;
private String email;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "specialist", nullable = false)
private Specialist specialist;
private LocalDateTime time;
boolean active;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}

public int getTicketCode() {
	return ticketCode;
}
public void setTicketCode(int ticketCode) {
	this.ticketCode = ticketCode;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public Specialist getSpecialist() {
	return specialist;
}
public void setSpecialist(Specialist specialist) {
	this.specialist = specialist;
}
public LocalDateTime getTime() {
	return time;
}
public void setTime(LocalDateTime time) {
	this.time = time;
}
public boolean isActive() {
	return active;
}
public void setActive(boolean active) {
	this.active = active;
}

}
