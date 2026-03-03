package auto.auto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.auto.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional <User> findByUsername(String username);


}
