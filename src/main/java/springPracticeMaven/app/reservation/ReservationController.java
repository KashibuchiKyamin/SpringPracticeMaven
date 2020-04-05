package springPracticeMaven.app.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.format.annotation.DateTimeFormat;
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

import lombok.AllArgsConstructor;
import springPracticeMaven.domin.model.ReservableRoom;
import springPracticeMaven.domin.model.ReservableRoomId;
import springPracticeMaven.domin.model.Reservation;
import springPracticeMaven.domin.model.RoleName;
import springPracticeMaven.domin.model.User;
import springPracticeMaven.domin.service.reservation.AlreadyReservedException;
import springPracticeMaven.domin.service.reservation.ReservationService;
import springPracticeMaven.domin.service.reservation.UnavailableReservationException;
import springPracticeMaven.domin.service.room.RoomService;

@Controller
@RequestMapping("reservations/{date}/{roomId}")
@AllArgsConstructor
public class ReservationController {

	RoomService roomService;

	ReservationService reservationService;

	@ModelAttribute
	ReservationForm setUpForm() {
		ReservationForm form = new ReservationForm();

		// デフォルト値
		form.setStartTime(LocalTime.of(9, 0));
		form.setEndTime(LocalTime.of(10, 0));

		return form;
	}

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
		model.addAttribute("user", dummyUser());

		return "reservation/reserveForm";

	}

	/**
	 * 予約処理.
	 * 
	 * @param form
	 * @param bindingResult
	 * @param date
	 * @param roomId
	 * @param model
	 * @return
	 */
	@PostMapping
	public String reserve(@Validated ReservationForm form, BindingResult bindingResult,
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
		reservation.setUser(dummyUser());

		try {
			reservationService.reserve(reservation);
		} catch (UnavailableReservationException | AlreadyReservedException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}

		return "redirect:/reservations/{date}/{roomId}";
	}

	@PostMapping(params = "cancel")
	public String cancel(@RequestParam("reservationId") Integer reservationId, @PathVariable("roomId") Integer roomId,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, Model model) {
		User user = dummyUser();
		try {
			reservationService.cancel(reservationId, user);

		} catch (IllegalStateException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}

		return "redirect:/reservations/{date}/{roomId}";

	}

	private User dummyUser() {
		User user = new User();

		user.setUserId("taro-yamada");
		user.setFirstName("太郎");
		user.setLastName("山田");
		user.setRoleName(RoleName.USER);

		return user;
	}
}
