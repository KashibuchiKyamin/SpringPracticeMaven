package springPracticeMaven.app.registration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("addUser")
public class AddUserController {

	@ModelAttribute
	AddUserForm setUp() {
		return new AddUserForm();
	}
	
	@GetMapping
	String provideEntrance() {
		return "registration/addUserForm";
	}
	
}
