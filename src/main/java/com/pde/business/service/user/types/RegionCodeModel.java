package com.pde.business.service.user.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionCodeModel {

    private String name;
    private String alpha2;
    private String alpha3;

}
