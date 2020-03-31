package springPracticeMaven.domin.service.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import springPracticeMaven.domin.model.ReservableRoom;
import springPracticeMaven.domin.repository.room.ReservableRoomRepository;

@Service
@Transactional
@AllArgsConstructor
public class RoomService {
	ReservableRoomRepository reservableRoomRepository;
	public List<ReservableRoom> findReservableRooms(LocalDate date){
		return reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(date);
	}
}
