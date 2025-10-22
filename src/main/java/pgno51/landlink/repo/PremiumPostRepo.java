package pgno51.landlink.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pgno51.landlink.model.PremiumPost;

@Repository
public interface PremiumPostRepo extends JpaRepository<PremiumPost, Integer> {
}
