package dat.exception;

public class DatabaseException extends RuntimeException {
    private int statusCode;

    public DatabaseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
