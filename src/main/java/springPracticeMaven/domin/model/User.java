package springPracticeMaven.domin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -173869866879384455L;

	@Id
	private String userId;

	private String password;

	private String firstName;

	private String lastName;

	@Enumerated(EnumType.STRING)
	private RoleName rolename;

}
