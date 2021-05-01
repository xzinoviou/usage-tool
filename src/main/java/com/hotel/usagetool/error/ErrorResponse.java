package com.hotel.usagetool.error;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The error response for unsuccessful calls.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

  private int code;

  private String description;

  private String path;

  private String timestamp;

  private List<Violation> violations;
}
