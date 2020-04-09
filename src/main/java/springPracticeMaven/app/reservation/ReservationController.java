package springPracticeMaven.app.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import springPracticeMaven.domin.model.ReservableRoom;
import springPracticeMaven.domin.model.ReservableRoomId;
import springPracticeMaven.domin.model.Reservation;
import springPracticeMaven.domin.service.reservation.AlreadyReservedException;
import springPracticeMaven.domin.service.reservation.ReservationService;
import springPracticeMaven.domin.service.reservation.UnavailableReservationException;
import springPracticeMaven.domin.service.room.RoomService;
import springPracticeMaven.domin.service.user.ReservationUserDetails;

@Controller
@RequestMapping("reservations/{date}/{roomId}")
@RequiredArgsConstructor
public class ReservationController {

	// @RequiredArgsConstructor 
	// で全フィールドに対する初期化値を引数にとるコンストラクタを生成し、コンストラクタインジェクションとする。
	// また、Spring 4.3から、クラス内にコンストラクタがただ1つしかない場合は、 @Autowired が省略可能

	private final RoomService roomService;

	private final ReservationService reservationService;

	@ModelAttribute
	ReservationForm setUpForm() {
		ReservationForm form = new ReservationForm();

		// デフォルト値
		form.setStartTime(LocalTime.of(9, 0));
		form.setEndTime(LocalTime.of(10, 0));

		return form;
	}

	/**
	 * 予約画面表示.
	 * 
	 * @param date   - 予約日
	 * @param roomId - 会議室ID
	 * @param model  - viewにセットするModel
	 * @return
	 */
	@GetMapping
	String reserveForm(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
			@PathVariable("roomId") Integer roomId, Model model) {

		ReservableRoomId reservableRoomId = new ReservableRoomId(roomId, date);
		List<Reservation> reservations = reservationService.findReservations(reservableRoomId);
		List<LocalTime> timeList = Stream.iterate(LocalTime.of(0, 0), t -> t.plusMinutes(30)).limit(24 * 2)
				.collect(Collectors.toList());

		model.addAttribute("room", roomService.findMeetingRoom(roomId));
		model.addAttribute("reservations", reservations);
		model.addAttribute("timeList", timeList);
		return "reservation/reserveForm";

	}

	/**
	 * 予約処理.
	 * 
	 * @param form          - 予約情報のForm
	 * @param bindingResult - Formのバリデーション情報
	 * @param date          - 日付
	 * @param roomId        - 会議室ID
	 * @param model         - viewにセットするModel
	 * @return 予約画面にリダイレクトする
	 */
	@PostMapping
	public String reserve(@Validated ReservationForm form, BindingResult bindingResult,
			@AuthenticationPrincipal ReservationUserDetails userDetails,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
			@PathVariable("roomId") Integer roomId, Model model) {
		if (bindingResult.hasErrors()) {
			return reserveForm(date, roomId, model);
		}

		ReservableRoom reservableRoom = new ReservableRoom(new ReservableRoomId(roomId, date));

		Reservation reservation = new Reservation();
		reservation.setStartTime(form.getStartTime());
		reservation.setEndTime(form.getEndTime());
		reservation.setReservableRoom(reservableRoom);
		reservation.setUser(userDetails.getUser());

		try {
			reservationService.reserve(reservation);
		} catch (UnavailableReservationException | AlreadyReservedException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}

		return "redirect:/reservations/{date}/{roomId}";
	}

	/**
	 * 予約キャンセル.
	 * 
	 * @param userDetails   - ログインしたユーザの情報
	 * @param reservationId - 予約ID
	 * @param roomId        - 会議室ID
	 * @param date          - 日付
	 * @param model         - viewにセットするModel
	 * @return 予約画面にリダイレクトする
	 */
	@PostMapping(params = "cancel")
	public String cancel(@RequestParam("reservationId") Integer reservationId, @PathVariable("roomId") Integer roomId,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, Model model) {
		try {
			Reservation reservation = reservationService.findById(reservationId);
			reservationService.cancel(reservation);

		} catch (IllegalStateException | AccessDeniedException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}

		return "redirect:/reservations/{date}/{roomId}";

	}

}
