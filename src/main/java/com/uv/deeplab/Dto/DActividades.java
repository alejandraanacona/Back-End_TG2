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
public class DActividades implements Serializable {

    private Long actId;

    private String configDefecto; //S=ConfiguracionDefecto, N=NO ConfiguracionDefecto

    private String actLaser;

    private String actCamara;

    private String actOdometria;

    private String actMode;
}
