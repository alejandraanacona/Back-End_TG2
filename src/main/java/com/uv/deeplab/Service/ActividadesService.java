package com.uv.deeplab.Service;

import com.uv.deeplab.Dto.DActividades;
import com.uv.deeplab.Entities.Actividades;
import com.uv.deeplab.Repository.ActividadesRepository;
import com.uv.deeplab.Service.SupportFunctions.Validations;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActividadesService {

    public static final String ERRORGUARDADO = "";

    public static final String ERRORUPDATE = "";

    @Autowired
    private Validations validations;

    @Autowired
    private ActividadesRepository actividadesRepository;

    public void actividadesSave (DActividades dActividades){
        Console.logInfo("ActividadesService", "Se inicia solicitud de guardado de Actividades");

        //Validaciones
        Boolean isConfigDefault = validations.verifyConfirDefault(dActividades);
        Actividades actividades = new Actividades();
        Console.logInfo("ActividadesService", "Terminan las solicitudes de guardado solicitud de guardado de Actividades");

        if(isConfigDefault==true){
            Console.logInfo("ActividadesService", "Las actividades seleccionadas estan configurado por defecto");
            actividades.setActCamara(dActividades.getActCamara());
            actividades.setActLaser(dActividades.getActLaser());
            actividades.setActOdometria(dActividades.getActOdometria());
            actividades.setActMode(dActividades.getActMode());
            actividades.setConfigDefecto(dActividades.getConfigDefecto());
        }else{
            Console.logInfo("ActividadesService", "Las actividades seleccionadas NO estan configurado por defecto");
            actividades.setActCamara(dActividades.getActCamara());
            actividades.setActLaser(dActividades.getActLaser());
            actividades.setActOdometria(dActividades.getActOdometria());
            actividades.setActMode(dActividades.getActMode());
            actividades.setConfigDefecto(dActividades.getConfigDefecto());

        }
        Console.logInfo("ActividadesService", "Previo a la solicitud solicitud de guardado de Actividades");

        try{
            Console.logInfo("ActividadesService", "Se inicia el guardado de datos de Actividades");
            actividadesRepository.save(actividades);
        }catch (Exception e){
            Console.logError("ActividadesService", "Ocurrió un error en el guardado de datos. El sistema responde: "+e.getMessage());
        }
    }

    public void actividadesUpdate (DActividades dActividades){
        Console.logInfo("ActividadesService", "Se inicia solicitud de actualizado de Actividades");

        //Validaciones
        Boolean existsActivity = validations.verifyIfExitsActivity(dActividades);
        Actividades actividades = new Actividades();

        if(existsActivity==true){
            Console.logInfo("ActividadesService", "Se realiza proceso de ACTUALIZACIÓN");
            actividades.setActId(dActividades.getActId());
            actividades.setActCamara(dActividades.getActCamara());
            actividades.setActMode(dActividades.getActMode());
            actividades.setActOdometria(dActividades.getActOdometria());
            actividades.setActLaser(dActividades.getActLaser());
            actividades.setConfigDefecto(dActividades.getConfigDefecto());

        }else{
            Console.logInfo("ActividadesService", "Se realiza proceso de CREACIÓN");
            actividades.setActCamara(dActividades.getActCamara());
            actividades.setActLaser(dActividades.getActLaser());
            actividades.setActOdometria(dActividades.getActOdometria());
            actividades.setActMode(dActividades.getActMode());
            actividades.setConfigDefecto(dActividades.getConfigDefecto());
        }
        try{
            Console.logInfo("ActividadesService", "Se inicia el guardado de datos de Actividades");
            actividadesRepository.save(actividades);
        }catch (Exception e){
            Console.logError("ActividadesService", "Ocurrió un error en el guardado de datos. El sistema responde: "+e.getMessage());
        }
    }
}