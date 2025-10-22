package pgno51.landlink.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pgno51.landlink.model.SavedPosts;

import java.util.List;

@Repository
public interface SavedPostsRepo extends JpaRepository<SavedPosts, Long> {
    List<SavedPosts> findAllByUser_Id(int userId);
}
