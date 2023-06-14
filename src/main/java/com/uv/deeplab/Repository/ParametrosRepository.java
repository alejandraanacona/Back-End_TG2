package com.uv.deeplab.Repository;

import com.uv.deeplab.Entities.Actividades;
import com.uv.deeplab.Entities.Parametros;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface ParametrosRepository extends PagingAndSortingRepository<Parametros, Long> {

    @Query("SELECT param.actLaser from Parametros param")
    String getActLaser();

    @Query("SELECT param.actCamara from Parametros param")
    String getActCamara();

    @Query("SELECT param.actOdometria from Parametros param")
    String getActOdometria();

    @Query("SELECT param.actMode from Parametros param")
    String getActMode();

}
