package com.hotel.usagetool.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Violation {

  private String fieldName;

  private String message;

  private String supplied;
}
