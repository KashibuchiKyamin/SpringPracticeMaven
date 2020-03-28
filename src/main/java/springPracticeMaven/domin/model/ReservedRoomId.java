package springPracticeMaven.domin.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReservedRoomId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4069584179936293835L;

	private Integer roomId;

	private LocalDate reservedDate;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reservedDate == null) ? 0 : reservedDate.hashCode());
		result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ReservedRoomId other = (ReservedRoomId) obj;
		if (reservedDate == null) {
			if (other.reservedDate != null) {
				return false;
			}
		} else if (!reservedDate.equals(other.reservedDate)) {
			return false;
		}
		if (roomId == null) {
			if (other.roomId != null) {
				return false;
			}
		} else if (!roomId.equals(other.roomId)) {
			return false;
		}

		return true;
	}
}
