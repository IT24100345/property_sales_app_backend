package pgno51.landlink.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pgno51.landlink.model.User;

public interface UserRepo extends JpaRepository<User,Integer> {
}
