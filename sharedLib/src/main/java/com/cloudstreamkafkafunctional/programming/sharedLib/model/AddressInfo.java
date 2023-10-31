package com.cloudstreamkafkafunctional.programming.sharedLib.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressInfo {

  private String contactNumber;
  private String addressLine;
  private String postalCode;
  private String province;
  private String city;
}
