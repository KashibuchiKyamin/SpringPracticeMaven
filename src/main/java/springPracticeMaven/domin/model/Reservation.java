package springPracticeMaven.domin.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 予約クラス.
 *
 */
@Entity
@Getter
@Setter
public class Reservation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 852473685059393509L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;

	@Column(name = "start_time", columnDefinition = "TIME")
	private LocalTime startTime;

	@Column(name = "end_time", columnDefinition = "TIME")
	private LocalTime endTime;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "reserved_date"), @JoinColumn(name = "room_id") })
	private ReservableRoom reservableRoom;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * 予約日時の重複判定
	 * @param target
	 * @return 重複していた場合trueを返す
	 */
	public boolean overlap(Reservation target) {
		if (!Objects.equals(reservableRoom.getReservableRoomId(), target.getReservableRoom().getReservableRoomId())) {
			return false;
		}
		if (startTime.equals(target.getStartTime()) && endTime.equals(target.getEndTime())) {
			return true;
		}
		return target.getEndTime().isAfter(startTime) && endTime.isAfter(target.getStartTime());
	}
}
