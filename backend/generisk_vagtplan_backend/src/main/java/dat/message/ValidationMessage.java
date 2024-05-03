package dat.message;

import java.util.Map;

public record ValidationMessage(long timestamp, String message, Map<String, Object> args, Object value) { }