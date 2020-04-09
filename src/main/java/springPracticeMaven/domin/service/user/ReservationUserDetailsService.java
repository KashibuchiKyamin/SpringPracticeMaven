package springPracticeMaven.domin.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springPracticeMaven.domin.model.User;
import springPracticeMaven.domin.repository.user.UserRepository;

@RequiredArgsConstructor
@Service
public class ReservationUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " is not found."));

		return new ReservationUserDetails(user);
	}

}
