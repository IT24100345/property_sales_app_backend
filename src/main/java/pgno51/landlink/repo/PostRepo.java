package pgno51.landlink.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pgno51.landlink.model.Post;
import pgno51.landlink.model.VerificationStatus;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findAllByAuthor_Id(int authorId);

    List<Post> findAllPostsByVerificationStatus(VerificationStatus verificationStatus);
}
