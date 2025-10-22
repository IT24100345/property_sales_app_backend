package pgno51.landlink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Chat {
    @Id
    private Long id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;
}
