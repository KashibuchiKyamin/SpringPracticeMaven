package springPracticeMaven.domin.service.reservation;

public class NonExistentReservationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8458227792945018417L;

	public NonExistentReservationException(String message) {
		super(message);
	}

}
