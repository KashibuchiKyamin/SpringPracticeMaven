package springPracticeMaven.domin.service.reservation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ReservationService {

	ReservationRepository reservationRepository;

	ReservableRoomRepository reservableRoomRepository;

	/**
	 * 予約検索
	 * 
	 * @param reservableRoomId　- 検索対象の予約ID
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

		Optional<ReservableRoom> reservable = reservableRoomRepository.findById(reservableRoomId);
		reservable.orElseThrow(() -> new UnavailableReservationException("入力の日付・部屋の組み合わせは予約できません。"));

		boolean overlap = reservationRepository
				.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId).stream()
				.anyMatch(x -> x.overlap(reservation));

		if (overlap) {
			throw new AlreadyReservedException("入力の時間代はすでに予約済です。");
		}
		reservationRepository.save(reservation);
		return reservation;
	}

	/**
	 * 予約キャンセルメソッド
	 * @param reservationId - キャンセル対象の予約ID
	 * @param requestUser - キャンセル処理を行うユーザ
	 */
	public void cancel(Integer reservationId, User requestUser) {
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new NonExistentReservationException("対象の予約は存在しません。"));
		if (RoleName.ADMIN != requestUser.getRoleName() && 
				!Objects.equals(reservation.getUser().getRoleName(), requestUser.getRoleName())) {
			throw new IllegalStateException("要求されたキャンセルは許可できません");
		}
		reservationRepository.delete(reservation);
	}
}
