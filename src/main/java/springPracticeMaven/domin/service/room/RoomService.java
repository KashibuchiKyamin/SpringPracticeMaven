package springPracticeMaven.domin.service.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import springPracticeMaven.domin.model.MeetingRoom;
import springPracticeMaven.domin.model.ReservableRoom;
import springPracticeMaven.domin.repository.room.MeetingRoomRepository;
import springPracticeMaven.domin.repository.room.ReservableRoomRepository;

@Service
@Transactional
@AllArgsConstructor
public class RoomService {

	ReservableRoomRepository reservableRoomRepository;

	MeetingRoomRepository meetingRoomRepository;

	/**
	 * 指定日に予約可能な会議室の一覧取得.
	 * 
	 * @param date - 指定日
	 * @return 指定日に予約可能な会議室の一覧
	 */
	public List<ReservableRoom> findReservableRooms(LocalDate date) {
		return reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(date);
	}
	
	public MeetingRoom findMeetingRoom(Integer roomId) {
		return meetingRoomRepository.findById(roomId).orElseThrow(); // TODO:存在しない場合のExceptionを設定するE
	}
}
