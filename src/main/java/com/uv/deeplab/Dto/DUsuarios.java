package com.uv.deeplab.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class DUsuarios implements Serializable {



    private Long userId;

    @Size(max = 50)
    private String codigoUv;

    @Size(max = 50)
    private String nombre;

    @Size(max = 50)
    private String apellido;

    @Size(max = 50)
    private String email;

    @Size(max = 50)
    private String programaAcademico;

    @Size(max = 50)
    private String password;
}
