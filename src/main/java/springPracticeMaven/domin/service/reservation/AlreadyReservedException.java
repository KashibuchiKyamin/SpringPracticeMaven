package springPracticeMaven.domin.service.reservation;

/**
 * 予約済例外クラス.
 *
 */
public class AlreadyReservedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6172845268507400330L;

	public AlreadyReservedException(String message) {
		super(message);
	}
}
