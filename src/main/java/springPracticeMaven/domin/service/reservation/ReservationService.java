package springPracticeMaven.domin.service.reservation;

import java.util.List;
import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import springPracticeMaven.domin.model.ReservableRoom;
import springPracticeMaven.domin.model.ReservableRoomId;
import springPracticeMaven.domin.model.Reservation;
import springPracticeMaven.domin.model.RoleName;
import springPracticeMaven.domin.model.User;
import springPracticeMaven.domin.repository.reservation.ReservationRepository;
import springPracticeMaven.domin.repository.room.ReservableRoomRepository;

/**
 * 予約サービスクラス.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;

	private final ReservableRoomRepository reservableRoomRepository;

	/**
	 * 予約検索
	 * 
	 * @param reservableRoomId - 検索対象の予約ID
	 * @return 予約リスト
	 */
	public List<Reservation> findReservations(ReservableRoomId reservableRoomId) {
		return reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);
	}

	/**
	 * 予約処理
	 * 
	 * @param reservation - 予約内容
	 * @return 登録に使用したreservation
	 */
	public Reservation reserve(Reservation reservation) {
		ReservableRoomId reservableRoomId = reservation.getReservableRoom().getReservableRoomId();

		ReservableRoom reservable = reservableRoomRepository.findOneForUpdateByReservableRoomId(reservableRoomId);
		if (reservable == null) {
			throw new UnavailableReservationException("入力の日付・部屋の組み合わせは予約できません。");
		}

		boolean overlap = reservationRepository
				.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId).stream()
				.anyMatch(x -> x.overlap(reservation));

		if (overlap) {
			throw new AlreadyReservedException("入力の時間帯はすでに予約済です。");
		}
		reservationRepository.save(reservation);
		return reservation;
	}

	/**
	 * 予約キャンセルメソッド.
	 * 
	 * @param reservationId - キャンセル対象の予約ID
	 * @param requestUser   - キャンセル処理を行うユーザ
	 */
	public void cancel(Integer reservationId, User requestUser) {
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new IllegalStateException("対象の予約は存在しません。"));
		if (RoleName.ADMIN != requestUser.getRoleName()
				&& !Objects.equals(reservation.getUser().getRoleName(), requestUser.getRoleName())) {
			throw new AccessDeniedException("要求されたキャンセルは許可できません");
		}
		reservationRepository.delete(reservation);
	}

	/**
	 * 予約キャンセルメソッド.
	 * 
	 * @param reservation キャンセル対象の予約
	 */
	@PreAuthorize(value = "hasRole('ADMIN') or #reservation.user.userId == principal.user.userId")
	public void cancel(@P("reservation") Reservation reservation) {
		reservationRepository.delete(reservation);
	}
	
	public Reservation findById(Integer reservationId) {
		return reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalStateException("対象の予約は存在しません。"));
	}
	
	
}
