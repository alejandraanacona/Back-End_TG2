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
@Table(name = "actividades")

public class Actividades {

    public static final String ID_SEQ = "actividades_id_seq";

    @Id
    @NotNull
    @GeneratedValue(generator = ID_SEQ, strategy = SEQUENCE)
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long actId;

    private String configDefecto;

    private String actLaser;

    private String actCamara;

    private String actOdometria;

    private String actMode;

}
