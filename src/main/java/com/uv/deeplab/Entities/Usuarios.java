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
@Table(name = "usuarios")

public class Usuarios {

    public static final String ID_SEQ = "usuarios_id_seq";

    @Id
    @NotNull
    @GeneratedValue(generator = ID_SEQ, strategy = SEQUENCE)
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long userId;

    @Column(length = 50)
    private String codigoUv;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String apellido;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String numeroIdenti;

    @Column(length = 50)
    private String programaAcademico;

    @Column(length = 50)
    private String password;

}
