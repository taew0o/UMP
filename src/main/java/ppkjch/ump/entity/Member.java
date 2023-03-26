package ppkjch.ump.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member{

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String password;
}
