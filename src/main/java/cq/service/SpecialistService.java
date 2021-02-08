package cq.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cq.entity.QueueTicket;
import cq.entity.Specialist;
import cq.repository.SpecialistRepository;

@Service
public class SpecialistService {
	@Autowired
	SpecialistRepository specialistRepository;

	public LocalDateTime findNearestFreeTime(Specialist specialist) {
		LocalDateTime latestTime = specialist.getTickets().stream().map(u -> u.getTime()).max(LocalDateTime::compareTo)
				.orElse(null);

		LocalDateTime timeNow = LocalDateTime.now();
		LocalDateTime cancaledTime = checkForCandeledTickets(
				specialist.getTickets().stream().filter(x -> x.getTime().isAfter(timeNow)).collect(Collectors.toList()),
				specialist.getUsualServiceTime());
		if (cancaledTime != null) {
			return cancaledTime;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime workHoursFrom = LocalDateTime.of(LocalDate.now(),
				LocalTime.parse(specialist.getWorkHoursFrom(), formatter));
		LocalDateTime workHoursTo = LocalDateTime.of(LocalDate.now(),
				LocalTime.parse(specialist.getWorkHoursTo(), formatter));
		if (latestTime == null) {
			if (workHoursFrom.isBefore(timeNow) && (workHoursTo.isAfter(timeNow))) {
				return LocalDateTime.now();
			} else if (workHoursFrom.isAfter(timeNow)) {
				return workHoursFrom;
			} else {
				return workHoursFrom.plusDays(1);
			}
		} else if (latestTime.plusMinutes(specialist.getUsualServiceTime()).isBefore(workHoursTo)) {

			if ((latestTime.plusMinutes(specialist.getUsualServiceTime()).isBefore(timeNow))
					&& (timeNow.plusMinutes(specialist.getUsualServiceTime()).isBefore(workHoursTo))) {
				return timeNow;
			}

			else if (latestTime.plusMinutes(specialist.getUsualServiceTime()).isAfter(timeNow)) {
				return latestTime.plusMinutes(specialist.getUsualServiceTime());
			}
		}
		return findTimeNextDay(workHoursFrom.plusDays(1), workHoursTo.plusDays(1), latestTime,
				workHoursFrom.plusDays(1), specialist.getUsualServiceTime());
	}

	public LocalDateTime findTimeNextDay(LocalDateTime from, LocalDateTime to, LocalDateTime latest,
			LocalDateTime today, int serviceTime) {
		if (latest.isBefore(from)) {
			return from;
		} else if (latest.plusMinutes(serviceTime).isBefore(to)) {
			return latest.plusMinutes(serviceTime);
		} else {
			return findTimeNextDay(from.plusDays(1), to.plusDays(1), latest, today.plusDays(1), serviceTime);

		}
	}

	public LocalDateTime checkForCandeledTickets(List<QueueTicket> tickets, int serviceTime) {
		if ((tickets != null) && (tickets.size() > 0)) {
			LocalDateTime previousTime = tickets.get(0).getTime();
			int previousDay = previousTime.getDayOfMonth();
			tickets.sort(Comparator.comparing(QueueTicket::getTime));
			for (QueueTicket ticket : tickets) {
				if (previousDay == ticket.getTime().getDayOfMonth()) {
					if (Duration.between(previousTime, ticket.getTime()).toMinutes() == serviceTime) {
					} else if (Duration.between(previousTime, ticket.getTime()).toMinutes() > serviceTime) {
						return previousTime.plusMinutes(serviceTime);
					}

				}
				previousTime = ticket.getTime();
				previousDay = previousTime.getDayOfMonth();

			}
		}
		return null;
	}
}
