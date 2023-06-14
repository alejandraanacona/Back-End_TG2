package com.uv.deeplab.Service;

import org.springframework.data.jpa.repository.Query;

public interface ParametrosService {

    String getActLaser();

    String getActCamara();

    String getActOdometria();

    String getActMode();

}
