package com.uv.deeplab.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Time;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sesion")

public class Sesion {

    public static final String ID_SEQ = "sesion_id_seq";

    @Id
    @NotNull
    @GeneratedValue(generator = ID_SEQ, strategy = SEQUENCE)
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long sesionId;

    private Date fecha;

    private Time horaInicio;

    private Time horaFin;

    private String configUser;

}
