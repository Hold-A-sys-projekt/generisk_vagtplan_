package dat.exception;

import lombok.Getter;

@Getter
public class AuthorizationException extends Exception {

    private final int statusCode;

    public AuthorizationException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}