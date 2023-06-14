package com.uv.deeplab.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uv.deeplab.Enum.EtipoPrograma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Blob;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DProgramas implements Serializable {


    private Long id;

    private String nombreProg;

    private EtipoPrograma tipoPrograma;

    private String directorio;

    private String nombreArch;

    private Blob contProg;
}
