package springPracticeMaven.app.registration;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import springPracticeMaven.domin.service.user.AddUserService;

@Controller
@RequestMapping("addUser")
@AllArgsConstructor
public class AddUserController {

	private AddUserService addUserService;

	@GetMapping
	String provideEntrance(@ModelAttribute AddUserForm form) {
		return "registration/addUserForm";
	}

	@PostMapping
	String addUser(@Validated AddUserForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return provideEntrance(form);
		}
		
		addUserService.addUser(form);
		
		return "registration/resultOfAddUser";
	}
}
