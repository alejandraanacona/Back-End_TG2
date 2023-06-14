package com.uv.deeplab.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Time;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "videos")

public class Videos implements Serializable {

    public static final String ID_SEQ = "videos_id_seq";

    @Id
    @NotNull
    @GeneratedValue(generator = ID_SEQ, strategy = SEQUENCE)
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long videoId;

    private Date fechaCap;

    private Time horaCap;

    @Lob
    private Blob dataVideo;

}
