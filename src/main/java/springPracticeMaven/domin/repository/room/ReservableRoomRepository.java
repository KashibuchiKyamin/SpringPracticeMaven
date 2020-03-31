package springPracticeMaven.domin.repository.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import springPracticeMaven.domin.model.ReservableRoom;
import springPracticeMaven.domin.model.ReservableRoomId;

public interface ReservableRoomRepository extends JpaRepository<ReservableRoom, ReservableRoomId> {

	List<ReservableRoom> findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(LocalDate reservedDate);
	
}
