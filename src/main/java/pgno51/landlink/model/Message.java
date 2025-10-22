package pgno51.landlink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    private Long id;

    @ManyToOne
    private Chat chat;
    @ManyToOne
    private User sender;
    private String content;
    private LocalDateTime timestamp;
}
