package pgno51.landlink.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import pgno51.landlink.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Query("select u from User u where :role = any(u.roles)")
    List<User> findUsersByRole(String role);

    Optional<User> findUsersByUsername(String username);
}
