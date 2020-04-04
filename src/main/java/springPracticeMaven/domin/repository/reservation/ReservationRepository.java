package springPracticeMaven.domin.repository.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import springPracticeMaven.domin.model.ReservableRoomId;
import springPracticeMaven.domin.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	List<Reservation> findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(ReservableRoomId reservableRoomId);

}
