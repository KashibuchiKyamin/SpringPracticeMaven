package springPracticeMaven.domin.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

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

	private LocalDate startTime;

	private LocalDate endTime;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "reserved_date"), @JoinColumn(name = "room_id") })
	private ReservableRoom reservableRoom;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
