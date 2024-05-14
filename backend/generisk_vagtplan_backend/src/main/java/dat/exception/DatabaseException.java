package dat.exception;

import lombok.Getter;

@Getter
public class DatabaseException extends RuntimeException {

    private final int statusCode;

    public DatabaseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}