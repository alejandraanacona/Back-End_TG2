package com.uv.deeplab.Service;

import com.uv.deeplab.Dto.DParametros;
import com.uv.deeplab.Entities.Parametros;
import com.uv.deeplab.Repository.ParametrosRepository;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametrosService {

    public static final String ERRORGUARDADO = "";

    public static final String ERRORUPDATE = "";

    /*@Autowired
    private Validations validations;*/

    @Autowired
    private ParametrosRepository parametrosRepository;


    public void parametrosSave (DParametros dParametros){
        Console.logInfo("ParametrosService", "Se inicia solicitud de guardado de Actividades");

        //Validaciones
        //Boolean isConfigDefault = validations.verifyConfirDefault(dActividades);
        Parametros parametros = new Parametros();
        Console.logInfo("ParametrosService", "Terminan las solicitudes de guardado solicitud de guardado de Actividades");

        parametros.setParamCamara(dParametros.getParamCamara());
        parametros.setParamLaser(dParametros.getParamLaser());
        parametros.setParamVelMax(dParametros.getParamVelMax());
        parametros.setParamVelAngMax(dParametros.getParamVelAngMax());
        parametros.setParamMode(dParametros.getParamMode());


        Console.logInfo("ParametrosService", "Previo a la solicitud solicitud de guardado de Actividades");

        try{
            Console.logInfo("ParametrosService", "Se inicia el guardado de datos de Actividades");
            parametrosRepository.save(parametros);
        }catch (Exception e){
            Console.logError("ParametrosService", "Ocurrió un error en el guardado de datos. El sistema responde: "+e.getMessage());
        }
    }

    public void parametrosUpdate (DParametros dParametros){
        Console.logInfo("ParametrosService", "Se inicia solicitud de actualizado de Actividades");

        //Validaciones
       /* Boolean existsActivity = validations.verifyIfExitsActivity(dActividades);*/
        Parametros parametros = new Parametros();


            Console.logInfo("ParametrosService", "Se realiza proceso de ACTUALIZACIÓN");
            parametros.setParamCamara(dParametros.getParamCamara());
            parametros.setParamLaser(dParametros.getParamLaser());
            parametros.setParamVelMax(dParametros.getParamVelMax());
            parametros.setParamVelAngMax(dParametros.getParamVelAngMax());
            parametros.setParamMode(dParametros.getParamMode());


        try{
            Console.logInfo("ParametrosService", "Se inicia el guardado de datos de Actividades");
            parametrosRepository.save(parametros);
        }catch (Exception e){
            Console.logError("ParametrosService", "Ocurrió un error en el guardado de datos. El sistema responde: "+e.getMessage());
        }
    }
}