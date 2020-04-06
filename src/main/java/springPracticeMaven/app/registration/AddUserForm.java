package springPracticeMaven.app.registration;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6473387358766354737L;

	@NotNull(message = "必須です")
	private String userId;

	@NotNull(message = "必須です")
	private String password;

	@NotNull(message = "必須です")
	private String firstName;

	@NotNull(message = "必須です")
	private String lastName;
	
}
