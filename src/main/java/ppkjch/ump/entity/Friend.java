package ppkjch.ump.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Friend {
    @Id
    @GeneratedValue
    @Column(name = "friend_id")
    private Long id;

    @OneToOne(mappedBy = "friend", fetch = FetchType.LAZY)
    private User user1;

    @OneToOne(mappedBy = "friend", fetch = FetchType.LAZY)
    private User user2;

}
