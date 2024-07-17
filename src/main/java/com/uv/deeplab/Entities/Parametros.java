package com.uv.deeplab.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parametros")

public class Parametros {

    public static final String ID_SEQ = "parametros_id_seq";

    @Id
    @NotNull
    @GeneratedValue(generator = ID_SEQ, strategy = SEQUENCE)
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long paramId;

    private Long userId;

    private String paramLaser;

    private String paramCamara;

    private Float paramVelMax;

    private Float paramVelAngMax;

    private Boolean paramMode;

    private String nombrePkg;

}
