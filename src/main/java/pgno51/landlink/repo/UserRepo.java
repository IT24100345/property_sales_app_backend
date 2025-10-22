package pgno51.landlink.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pgno51.landlink.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

//    @Query("select u from User u where :role = any(u.roles)")
//    List<User> findUsersByRole(String role);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username = ?2, u.email = ?3 WHERE u.id = ?1")
    void updateUserProfileById(int id, String username, String email);

    Optional<User> findUsersByUsername(String username);
}
