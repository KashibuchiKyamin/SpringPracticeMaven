package springPracticeMaven.domin.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import springPracticeMaven.domin.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
