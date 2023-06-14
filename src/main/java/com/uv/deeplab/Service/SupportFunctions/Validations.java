package com.uv.deeplab.Service.SupportFunctions;

import com.uv.deeplab.Dto.DActividades;
import com.uv.deeplab.Entities.Actividades;
import com.uv.deeplab.Repository.ActividadesRepository;
import com.uv.deeplab.Repository.ParametrosRepository;
import com.uv.deeplab.Service.ParametrosService;
import com.uv.deeplab.config.Console;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Validations {

    @Autowired
    private final ParametrosService parametrosService;

    @Autowired
    private ActividadesRepository actividadesRepository;

    //Validación actLaser
    public boolean verifyIsActLaserIsConfigDefect(String actLaserFront){
        Boolean answer;
        String actLaserConfigDefect = parametrosService.getActLaser();
        if(actLaserConfigDefect==actLaserFront){
            answer=true;
        }else{
            answer=false;
        }
        return answer;
    }

    //Validación actCamara
    public boolean verifyIsActCamaraIsConfigDefect(String actCamaraFront){
        Boolean answer;
        String actCamaraConfigDefect = parametrosService.getActCamara();
        if(actCamaraConfigDefect==actCamaraFront){
            answer=true;
        }else{
            answer=false;
        }
        return answer;
    }

    //Validación actOdometria
    public boolean verifyIsActOdometriaIsConfigDefect(String actOdometriaFront){
        Boolean answer;
        String actOdometriaConfigDefect = parametrosService.getActLaser();
        if(actOdometriaConfigDefect==actOdometriaFront){
            answer=true;
        }else{
            answer=false;
        }
        return answer;
    }

    //Validación actMode
    public boolean verifyIsActModeIsConfigDefect(String actModeFront){
        Boolean answer;
        String actModeConfigDefect = parametrosService.getActLaser();
        if(actModeConfigDefect==actModeFront){
            answer=true;
        }else{
            answer=false;
        }
        return answer;
    }

    //Validación si es ConfigDefault o no
    public boolean verifyConfirDefault(DActividades dActividades){
        Console.logInfo("ActividadesController", "Se obtiene la siguiente actividad del front: "+dActividades);

        Boolean isConfigDefault;
        Boolean actLaser = verifyIsActLaserIsConfigDefect(dActividades.getActLaser());
        Boolean actCamara = verifyIsActCamaraIsConfigDefect(dActividades.getActCamara());
        Boolean actOdometria = verifyIsActOdometriaIsConfigDefect(dActividades.getActOdometria());
        Boolean actMode = verifyIsActModeIsConfigDefect(dActividades.getActMode());

        if((actLaser==true)&&(actCamara==true)&&(actOdometria==true)&&(actMode==true)){
            isConfigDefault=true;
        }else{
            isConfigDefault=false;
        }
        return isConfigDefault;
    }

    //Validación para saber si la actividad ya esta registrada o no
    public boolean verifyIfExitsActivity(DActividades dActividades){
        Boolean existsActivity;
        Optional<Actividades> optionalActividades = actividadesRepository.findById(dActividades.getActId());
        Actividades actividades = new Actividades();

        if(optionalActividades.isPresent()){
            existsActivity=true;
        }else{
            existsActivity=false;
        }
        return existsActivity;
    }


}
