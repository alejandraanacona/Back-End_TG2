package com.uv.deeplab.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Time;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class DVideos implements Serializable {

    private Long id;

    private Date fechaCap;

    private Time horaCap;

    private Blob dataVideo;
}
