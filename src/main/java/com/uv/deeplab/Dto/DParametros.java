package com.uv.deeplab.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DParametros implements Serializable {

    private Long paramId;

    private Long userId;

    private String paramLaser;

    private String paramCamara;

    private Float paramVelMax;

    private Float paramVelAngMax;

    private Boolean paramMode;

    private String nombrePkg;
}
