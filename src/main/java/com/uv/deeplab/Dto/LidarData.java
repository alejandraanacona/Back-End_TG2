package com.uv.deeplab.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class LidarData implements Serializable {

        //private Long id;
        private Double angleMin;

        private Double angleMax;

        private Double angleIncre;

        private Double timeIncre;

        private Double scanTime;

        private Double rangeMin;

        private Double rangeMax;

        private List<Double> ranges;

        private List<Double> intensities;

    }

