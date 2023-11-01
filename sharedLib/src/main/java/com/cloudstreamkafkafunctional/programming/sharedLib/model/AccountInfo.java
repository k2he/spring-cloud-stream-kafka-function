package com.cloudstreamkafkafunctional.programming.sharedLib.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

  private String acocuntNumber;
  private String firstName;
  private String lastName;
  private String middleName;
  private AddressInfo address;
  private LocalDateTime lastUpdated;
}
