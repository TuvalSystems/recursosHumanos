package tuval.sistemas.recursosHumanos.common.response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ApiResponseFactory {

  private ApiResponseFactory() {}


  public static ResponseEntity<Map<String, Object>> internalServerError(String message) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    response.put("message", message);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  public static ResponseEntity<Map<String, Object>> fromException(Exception exception) {
    return internalServerError(exception.getMessage());
  }

  public static ResponseEntity<Map<String, Object>> success(
      String message, Object data) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", message);
    response.put("data", data);
    return ResponseEntity.ok(response);
  }

  public static ResponseEntity<Map<String, Object>> success(String message) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", message);
    return ResponseEntity.ok(response);
  }

  public static ResponseEntity<Map<String, Object>> badRequest(String message) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    response.put("message", message);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
}
