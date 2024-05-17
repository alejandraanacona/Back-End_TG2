package com.uv.deeplab.Entities;

import com.uv.deeplab.Enum.EtipoPrograma;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Blob;

import static javax.persistence.GenerationType.SEQUENCE;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "programas")

public class Programas {

    public static final String ID_SEQ = "programas_id_seq";

    @Id
    @NotNull
    @GeneratedValue(generator = ID_SEQ, strategy = SEQUENCE)
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long Id;

    private Long userId;

    private String nameFolder;

    private String pkgName;

    private String path;

    private String parentId;




}
