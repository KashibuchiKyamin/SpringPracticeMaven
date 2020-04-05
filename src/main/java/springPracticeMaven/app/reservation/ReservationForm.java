package springPracticeMaven.app.reservation;

import java.io.Serializable;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EndTimeMustBeAfterStartTime(message = "終了時間は開始時刻より後に設定してください。")
public class ReservationForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3640125512552592865L;

	@NotNull(message = "必須です")
	@DateTimeFormat(pattern = "HH:mm")
	@ThirtyMinutesUnit(message = "30分単位で入力してください。")
	private LocalTime startTime;

	@NotNull(message = "必須です")
	@DateTimeFormat(pattern = "HH:mm")
	@ThirtyMinutesUnit(message = "30分単位で入力してください。")
	private LocalTime endTime;

}
