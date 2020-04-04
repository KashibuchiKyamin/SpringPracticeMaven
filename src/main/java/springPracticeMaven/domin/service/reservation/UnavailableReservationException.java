package springPracticeMaven.domin.service.reservation;

/**
 * 予約不可例外クラス.
 *
 */
public class UnavailableReservationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6939539489858002063L;
	
	public UnavailableReservationException(String message) {
		super(message);
	}

}
