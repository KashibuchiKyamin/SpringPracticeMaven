package springPracticeMaven.domin.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import springPracticeMaven.domin.model.User;
import springPracticeMaven.domin.repository.user.UserRepository;

@AllArgsConstructor
@Service
public class ReservationUserDetailsService implements UserDetailsService {

	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " is not found."));

		return new ReservationUserDetails(user);
	}

}
