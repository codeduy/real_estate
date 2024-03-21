package org.example.listingservice.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RealEstate {
    private Long id;
    private String title;
    private String RealEstateType;
    private String phoneNumber;

}
