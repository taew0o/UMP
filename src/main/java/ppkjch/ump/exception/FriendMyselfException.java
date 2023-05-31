package ppkjch.ump.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendMyselfException extends RuntimeException{
    public FriendMyselfException(String message) {
        super(message);
    }
}
