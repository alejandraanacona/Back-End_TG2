package com.uv.deeplab.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class DUsuarios implements Serializable {

    private Long id;

    @Size(max = 50)
    private String codigoUv;

    @Size(max = 50)
    private String nombre;

    @Size(max = 50)
    private String apellido;

    @Size(max = 50)
    private String email;

    @Size(max = 50)
    private String numeroIdenti;

    @Size(max = 50)
    private String programaAcademico;

    @Size(max = 50)
    private String password;
}
