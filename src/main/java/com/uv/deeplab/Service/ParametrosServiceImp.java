package com.uv.deeplab.Service;

import com.uv.deeplab.Repository.ParametrosRepository;
import com.uv.deeplab.Service.ParametrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametrosServiceImp implements ParametrosService {

    @Autowired
    private ParametrosRepository parametrosRepository;


    @Override
    public String getActLaser() {
        return parametrosRepository.getActLaser();
    }

    @Override
    public String getActCamara() {
        return parametrosRepository.getActCamara();
    }

    @Override
    public String getActOdometria() {
        return parametrosRepository.getActOdometria();
    }

    @Override
    public String getActMode() {
        return parametrosRepository.getActMode();
    }
}
