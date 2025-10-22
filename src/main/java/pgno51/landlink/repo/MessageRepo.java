package pgno51.landlink.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pgno51.landlink.model.Message;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,Integer> {
    List<Message> findByChatIdOrderByTimestampAsc(Long chatId);
}
