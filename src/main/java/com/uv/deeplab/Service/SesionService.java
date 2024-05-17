package com.uv.deeplab.Service;

import com.uv.deeplab.Entities.Sesion;
import com.uv.deeplab.Repository.SesionRepository;
import com.uv.deeplab.config.Console;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class SesionService {

    private final SesionRepository sesionRepository;

    public void registerSession(Long userId) {
        Console.logInfo("entra en funcion sesión", "si entra a eregistro sesion");

        Sesion sesion = new Sesion();
        sesion.setUserId(userId);
        sesion.setFecha(new Date());
        sesion.setHoraInicio(new Time(System.currentTimeMillis()));
        sesionRepository.save(sesion);
    }
}
