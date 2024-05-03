package dat.exception;

import lombok.Getter;

@Getter
public class ApiException extends Exception {

    private final int statusCode;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}