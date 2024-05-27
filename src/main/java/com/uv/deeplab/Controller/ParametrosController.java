package com.uv.deeplab.Controller;

import com.uv.deeplab.Dto.DParametros;
import com.uv.deeplab.Service.ParametrosService;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/parametros")
public class ParametrosController {

    @Autowired
    ParametrosService parametrosService;

    @PostMapping("/save/")
    public void saveParametros(@RequestBody DParametros dParametros) throws Exception {
        Console.logInfo("ParametrosController", "Se obtiene los siguientes parametros del front: "+dParametros);
        parametrosService.parametrosSave(dParametros);
    }

    @PostMapping("/update/")
    public void updateParametros(@RequestBody DParametros dParametros){
        Console.logInfo("ParametrosController", "Se obtiene la siguiente actividad del front: "+dParametros);
        parametrosService.parametrosUpdate(dParametros);
    }
}
