package springPracticeMaven.domin.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;

import springPracticeMaven.domin.model.MeetingRoom;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {

}
