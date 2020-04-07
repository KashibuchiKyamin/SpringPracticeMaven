package springPracticeMaven.domin.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import springPracticeMaven.app.registration.AddUserForm;
import springPracticeMaven.domin.model.RoleName;
import springPracticeMaven.domin.model.User;
import springPracticeMaven.domin.repository.user.UserRepository;

@Service
@AllArgsConstructor
@Transactional
public class AddUserService {

	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;
	
	public void addUser(AddUserForm form) {
		User user = new User();		
		user.setUserId(form.getUserId());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setRoleName(RoleName.USER);
		
		User result = userRepository.saveAndFlush(user);
		if (result == null) {
			throw new RuntimeException("登録に失敗しました。");
		}
		
	}
	
}
