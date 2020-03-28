package springPracticeMaven.domin.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReservableRoom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7424239620329824230L;

	@EmbeddedId
	private ReservedRoomId reservedRoomId;

	@ManyToOne
	@JoinColumn(name = "roomId", insertable = false, updatable = false)
	@MapsId("roomId")
	private MeetingRoom meetingRoom;

	public ReservableRoom(ReservedRoomId reservedRoomId) {
		this.reservedRoomId = reservedRoomId;
	}

}
