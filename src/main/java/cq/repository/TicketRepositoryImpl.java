package cq.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cq.entity.QueueTicket;
import cq.entity.Specialist;


@Repository
public interface TicketRepositoryImpl extends JpaRepository<QueueTicket, Long> {
	Optional<QueueTicket> findFirstByOrderByTicketCodeDesc();
	Optional<QueueTicket> findFirstByOrderByTicketCode();
	Optional<QueueTicket> findByTicketCode(int code);
	Optional<List<QueueTicket>> findByEmail(String email);
	boolean existsBySpecialistAndActive(Specialist specialist, boolean active);
	Optional<QueueTicket> findFirstBySpecialistAndActive(Specialist specialist, boolean active);
	Optional<List<QueueTicket>> findByActive(boolean active);
	Optional<List<QueueTicket>> findTop5ByActiveOrderByTime(boolean active);
}
