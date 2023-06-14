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
    private Long parameterId;

    private String actLaser;

    private String actCamara;

    private String actOdometria;

    private String actMode;

}
