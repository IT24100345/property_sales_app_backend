package pgno51.landlink.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int landArea;
    private int views;
    private int inquiries;
    private float price;
    private String title;
    private String description;
    private String location;
    private String contactPhone;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private String[] images;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private String[] features;

    @Enumerated(EnumType.STRING)
    private PropertyType type;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;

    @ManyToOne
    //@JsonBackReference
    @JsonIgnore
    @JoinColumn(name = "author_id")
    private User author;

    @OneToOne(mappedBy = "post")
    private PremiumPost premiumPost;

}
