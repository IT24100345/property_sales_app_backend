package pgno51.landlink.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pgno51.landlink.model.Chat;

@Repository
public interface ChatRepo extends JpaRepository<Chat,Integer> {
    @Query("SELECT c FROM Chat c WHERE (c.user1.id = :user1Id AND c.user2.id = :user2Id) OR (c.user1.id = :user2Id AND c.user2.id = :user1Id)")
    Chat findChatBetweenUsers(@Param("user1Id") int user1Id, @Param("user2Id") int user2Id);
}
