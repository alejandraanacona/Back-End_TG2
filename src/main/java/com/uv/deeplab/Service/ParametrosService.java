package com.uv.deeplab.Service;

import com.uv.deeplab.Controller.WebSocketController;
import com.uv.deeplab.Dto.DParametros;
import com.uv.deeplab.Dto.DUsuarios;
import com.uv.deeplab.Entities.Parametros;
import com.uv.deeplab.Entities.Programas;
import com.uv.deeplab.Repository.ParametrosRepository;
import com.uv.deeplab.config.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ParametrosService {

    public static final String ERRORGUARDADO = "";

    public static final String ERRORUPDATE = "";

    /*@Autowired
    private Validations validations;*/

    @Autowired
    private ParametrosRepository parametrosRepository;

    @Autowired
    private ProgramasService programasService;

    @Autowired
    private WebSocketController webSocketController;


    public void parametrosSave(DParametros dParametros) throws Exception {
        Console.logInfo("ParametrosService", "Se inicia solicitud de guardado de Parametros");

        Programas programas = new Programas();

        programas = programasService.consultarPath(dParametros.getUserId());
        programas.setNameFolder(dParametros.getNombrePkg());
        String path=programas.getPath();

        String nombrepkg = dParametros.getNombrePkg();
        String command = "ros2 pkg create --build-type ament_python --node-name my_node " + nombrepkg;

        //String[] commandCreatePkg = {ros2Executable, "pkg", "create", "--build-type", "ament_python", "--node-name", "my_node", nombrepkg};

        // Validaciones
        Parametros parametros = new Parametros();
        Console.logInfo("ParametrosService", "Terminan las solicitudes de guardado solicitud de guardado de Parametros");

        parametros.setParamCamara(dParametros.getParamCamara());
        parametros.setUserId(dParametros.getUserId());
        parametros.setParamLaser(dParametros.getParamLaser());
        parametros.setParamVelMax(dParametros.getParamVelMax());
        parametros.setParamVelAngMax(dParametros.getParamVelAngMax());
        parametros.setParamMode(dParametros.getParamMode());
        parametros.setNombrePkg(dParametros.getNombrePkg());

        Console.logInfo("ParametrosService", "Previo a la solicitud solicitud de guardado de Actividades");


        try {
            Console.logInfo("ParametrosService", "Se inicia el guardado de datos de Actividades");
            parametrosRepository.save(parametros);
        } catch (Exception e) {
            Console.logError("ParametrosService", "Ocurrió un error en el guardado de datos. El sistema responde: " + e.getMessage());
            throw e; // Re-lanza la excepción para informar de este error crítico.
        }

        try {
            File directory = new File(path);
            if (!directory.exists() || !directory.isDirectory()) {
                throw new IOException("El directorio especificado no existe: " + path);
            }

            Console.logInfo("inicia creacion del paquete de ros2", "ws de usuario");

            // Configurar el entorno con la variable PATH correcta
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
            processBuilder.directory(directory);
            Map<String, String> environment = processBuilder.environment();
            environment.put("AMENT_PREFIX_PATH", "/opt/ros/foxy");
            environment.put("PYTHONPATH", "/opt/ros/foxy/lib/python3.8/site-packages");
            environment.put("LD_LIBRARY_PATH", "/opt/ros/foxy/opt/yaml_cpp_vendor/lib:/opt/ros/foxy/opt/rviz_ogre_vendor/lib:/opt/ros/foxy/lib/x86_64-linux-gnu:/opt/ros/foxy/lib");
            environment.put("PATH", "/opt/ros/foxy/bin:" + System.getenv("PATH"));

            Process process = processBuilder.start();

            // Capturar la salida estándar y de error
            StringBuilder output = new StringBuilder();
            StringBuilder errorOutput = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                while ((line = errorReader.readLine()) != null) {
                    errorOutput.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                Console.logInfo("pkg creado en ws", "exitosamente: " + output.toString());
                programasService.savePath(programas);
                Console.logInfo("se guardó el registro de", "el path en base de datos");
            } else {
                Console.logError("Error al crear ws en Ubuntu", "Código de salida: " + exitCode + "\nError: " + errorOutput.toString());
            }
        } catch (IOException | InterruptedException e) {
            Console.logError("Error al ejecutar comando en Ubuntu", e.getMessage());
            throw e; // Re-lanza la excepción para informar de este error crítico.
        }
    }

    private String executeCommand(String command, File directory, Map<String, String> environment) throws IOException, InterruptedException {
        StringBuilder outputBuilder = new StringBuilder(); // Para almacenar la salida del proceso

        ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
        processBuilder.directory(directory);
        if (environment != null) {
            Map<String, String> processEnv = processBuilder.environment();
            processEnv.putAll(environment);
        }

        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Console.logInfo("Process Output", line);
                webSocketController.sendOutput(line); // Enviar la salida al frontend
                outputBuilder.append(line).append("\n"); // Agregar la línea al StringBuilder
            }

            while ((line = errorReader.readLine()) != null) {
                Console.logError("Process Error", line);
                webSocketController.sendOutput("ERROR: " + line); // Enviar el error al frontend
                outputBuilder.append("ERROR: ").append(line).append("\n"); // Agregar el error al StringBuilder
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command failed with exit code: " + exitCode);
        }

        return outputBuilder.toString(); // Devolver la salida completa del proceso
    }

    /*public String buildAndRunPackage(Long userId, String nombrepkg) throws Exception {
        Console.logInfo("ParametrosService", "Inicia la construcción y ejecución del paquete ROS 2");

        Programas programas = programasService.consultarPath(userId);
        String path= programas.getPath();

        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IOException("El directorio especificado no existe: " + path);
        }

        try {
            // Crear un nuevo mapa de entorno basado en las variables de entorno existentes
            Map<String, String> environment = new HashMap<>(System.getenv());
            environment.put("AMENT_PREFIX_PATH", "/opt/ros/foxy");
            environment.put("PYTHONPATH", "/opt/ros/foxy/lib/python3.8/site-packages");
            environment.put("LD_LIBRARY_PATH", "/opt/ros/foxy/opt/yaml_cpp_vendor/lib:/opt/ros/foxy/opt/rviz_ogre_vendor/lib:/opt/ros/foxy/lib/x86_64-linux-gnu:/opt/ros/foxy/lib");
            environment.put("PATH", "/opt/ros/foxy/bin:" + environment.get("PATH"));

            // Crear el script shell para ejecutar los comandos ROS 2
            String scriptContent =
                    "#!/bin/bash\n" +
                            "source /opt/ros/foxy/setup.bash\n" +
                            "colcon build --packages-select " + nombrepkg + "\n" +
                            "source " + path + "/install/setup.bash\n" ;

            File scriptFile = new File(directory, "execute_ros2.sh");
            try (FileWriter writer = new FileWriter(scriptFile)) {
                writer.write(scriptContent);
            }

            // Hacer que el script sea ejecutable
            scriptFile.setExecutable(true);

            // Ejecutar el script shell y capturar la salida
            String output = executeCommand("./execute_ros2.sh", directory, environment);

            Console.logInfo("Ejecutando comando", "ros2 run " + nombrepkg + " my_node");
            executeCommand( "ros2 run " + nombrepkg + " my_node", directory, environment);
            Console.logInfo("ros2 run", "ejecutado exitosamente");
            webSocketController.sendOutput(output);
            return output; // Devolver la salida del script


        } catch (IOException | InterruptedException e) {
            Console.logError("Error al ejecutar comando en Ubuntu", e.getMessage());
            webSocketController.sendOutput("ERROR: " + e.getMessage());
            throw e;
        }
    }*/
    public Process runningProcess;

    private Long processGroupPid;

    public void killProcessesByPath(String path) {
        try {
            // Buscar procesos que coincidan con la ruta dada
            ProcessBuilder psBuilder = new ProcessBuilder("ps", "-ef");
            Process psProcess = psBuilder.start();

            // Leer la salida del comando ps
            BufferedReader psReader = new BufferedReader(new InputStreamReader(psProcess.getInputStream()));
            String line;
            List<Integer> pids = new ArrayList<>();
            while ((line = psReader.readLine()) != null) {
                if (line.contains(path)) {
                    String[] parts = line.trim().split("\\s+");
                    // El PID generalmente está en la segunda columna
                    int pid = Integer.parseInt(parts[1]);
                    pids.add(pid);
                }
            }

            // Matar los procesos encontrados
            for (int pid : pids) {
                System.out.println("Killing process with PID: " + pid);
                ProcessBuilder killBuilder = new ProcessBuilder("kill", "-9", String.valueOf(pid));
                Process killProcess = killBuilder.start();
                killProcess.waitFor();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void buildAndRunPackage(Long userId, String nombrepkg) throws Exception {
        Console.logInfo("ParametrosService", "Inicia la construcción y ejecución del paquete ROS 2");

        Programas programas = programasService.consultarPath(userId);
        String path = programas.getPath();
        String pathLibreria = "/home/servidor/Documentos/ProyectoDeepLabUv/Backend_deep_lab_uv/aws-deepracer-interfaces-pkg";

        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IOException("El directorio especificado no existe: " + path);
        }

        try {
            Map<String, String> environment = new HashMap<>(System.getenv());
            environment.put("AMENT_PREFIX_PATH", "/opt/ros/foxy");
            environment.put("PYTHONPATH", "/opt/ros/foxy/lib/python3.8/site-packages");
            environment.put("LD_LIBRARY_PATH", "/opt/ros/foxy/opt/yaml_cpp_vendor/lib:/opt/ros/foxy/opt/rviz_ogre_vendor/lib:/opt/ros/foxy/lib/x86_64-linux-gnu:/opt/ros/foxy/lib");
            environment.put("PATH", "/opt/ros/foxy/bin:" + environment.get("PATH"));

            // Construir el comando
            String[] command = {
                    "/bin/bash", "-c",
                    "source /opt/ros/foxy/setup.bash && " +
                            "export ROS_DOMAIN_ID=27 && " +
                            "source " + pathLibreria + "/install/setup.bash && " +
                            "colcon build --packages-select " + nombrepkg + " && " +
                            "source " + path + "/install/setup.bash && " +
                            "ros2 run " + nombrepkg + " my_node"
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(directory);
            processBuilder.environment().putAll(environment);
            processBuilder.redirectErrorStream(true);

            runningProcess = processBuilder.start();

            Executors.newSingleThreadExecutor().submit(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(runningProcess.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Console.logInfo("Output from ROS2 node", line);
                        webSocketController.sendOutput(line); // Enviar la salida al frontend
                    }
                } catch (IOException e) {
                    Console.logError("Error al leer la salida del proceso ROS2", e.getMessage());
                    webSocketController.sendOutput("ERROR: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            Console.logError("Error al ejecutar comando en Ubuntu", e.getMessage());
            webSocketController.sendOutput("ERROR: " + e.getMessage());
            throw e;
        }

    }


    // Método para detener el proceso
    public void stopRunningProcess(Long userId) throws Exception {

        Programas programas = programasService.consultarPath(userId);
        String path = programas.getPath();

        if (runningProcess != null) {
            Console.logInfo("Deteniendo el proceso en ejecución.", "del proyecto");
            try {
                // Intentar destruir el proceso normalmente
                runningProcess.destroy();
                if (!runningProcess.waitFor(5, TimeUnit.SECONDS)) {
                    runningProcess.destroyForcibly();
                }
                runningProcess = null; // Eliminar la referencia al proceso detenido
                webSocketController.sendOutput("Stop proceso");

                // Asegurarse de matar todos los procesos relacionados con el directorio de trabajo
                //String pathProcess = "/home/servidor/Documentos/wssDeepLabUV/vaneanac"; // Ajustar la ruta según sea necesario
                killProcessesByPath(path);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Console.logError("Error al detener el proceso ROS2", e.getMessage());
                webSocketController.sendOutput("ERROR: " + e.getMessage());
            }
        } else {
            Console.logInfo("No hay ningún proceso en ejecución que detener.", "del proyecto");
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