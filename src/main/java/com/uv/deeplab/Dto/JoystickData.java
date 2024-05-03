package com.uv.deeplab.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JoystickData implements Serializable {

    private Double identifier;
    private Position position;
    private Double force;
    private Double distance;
    private Double pressure;
    private Angle angle;
    private Vector vector;
    private Raw raw;
    private Object instance;

   /* @Builder.Default
    private Double angle2 =1.0;
    @Builder.Default
    private Double throttle= 1.0;*/

}

