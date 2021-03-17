package com.example.iticket.repository;

import com.example.iticket.model.TicketReservation;
import com.example.iticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TicketReservationRepository extends CrudRepository<TicketReservation, Long> {

    @Modifying
    @Query("delete from TicketReservation t where t.ticketReservationId=:ticketReservationId")
    public void deleteById(@Param("ticketReservationId") Long ticketReservationId);

    public List<TicketReservation> findAllByUser(User user);
}
