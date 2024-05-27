package com.uv.deeplab.Service;

import com.uv.deeplab.Dto.DUsuarios;
import com.uv.deeplab.Entities.Programas;
import com.uv.deeplab.Entities.Usuarios;

import com.uv.deeplab.Mapper.UsuariosMapper;
import com.uv.deeplab.Repository.UsuariosRepository;
import com.uv.deeplab.config.Console;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@Service
@Log4j2
@RequiredArgsConstructor
@ComponentScan
public class UsuariosService {

    private final UsuariosMapper mapper = Mappers.getMapper(UsuariosMapper.class);
    private final UsuariosRepository usuariosRepository;
    private final EmailService emailService;
    //@Autowired


    @Autowired
    SesionService sesionService;

    @Autowired
    ProgramasService programasService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuarios findByCode(String codigoUv) {

        return usuariosRepository.findByCodigoUv(codigoUv);
    }


    //@Override
    public Usuarios update(String codigoUv) throws MessagingException {
        Usuarios user = usuariosRepository.findByCodigoUv(codigoUv);
        String email = user.getEmail();
        String subject = "Recuperación contraseña";
        String prebody = "Hola, has solicitado un cambio de contraseña, tu contraseña ha sido modificada por la siguiente contraseña provisional: ";
        String attachment = "esto se debe borrar";

        String rand = "";
        String chars = "1234567890-=@#*+qwertyuiopQWERTYUOPasdfghjkASDFGHJKLzxcvbnm,.ZXCVBNM";
        for (int i = 0; i < 10; i++) {

            rand += chars.toCharArray()[new Random().nextInt(chars.length())];

        }
        user.setPassword(rand);
        String body = prebody + rand;
        emailService.sendEmailWithAttachment(email, body, subject, attachment);

        return usuariosRepository.save(user);
    }

    public Usuarios guardarUsuario(Usuarios usuarios){
        usuariosRepository.save(usuarios);
        return (usuarios);
    };

    public CreateMessage create(DUsuarios dusuarios)throws MessagingException{
        Console.logInfo("entra al service", "para crear usuario " +dusuarios);
        Usuarios entity= mapper.fromDto(dusuarios);
        entity.setPassword(dusuarios.getCodigoUv());
        guardarUsuario(entity);

        String username = dusuarios.getApellido();


        String path="/home/servidor/wssDeepLabUV/" + username +"_ws/src";

        String createUserCommand = "mkdir -p "+path;

        Programas programas= new Programas();
        programas.setUserId(dusuarios.getUserId());
        programas.setNameFolder(" ");
        programas.setParentId("wssDeepLabUV");
        programas.setPath(path);

        Console.logInfo("este es el comando" ,"para crear ws: "+createUserCommand);
        try {
            Process process = Runtime.getRuntime().exec(createUserCommand);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                Console.logInfo("ws creado en Ubuntu", "Usuario: " + username);
                programasService.savePath(programas);
                Console.logInfo("se guardó el registro de","el path en base de datos");
                return new CreateMessage("ws creado exitosamente y usuario uardado en la base de datos", true);
            } else {
                Console.logError("Error al crear ws en Ubuntu", "Código de salida: " + exitCode);
                return new CreateMessage("Error al crear ws en Ubuntu", false);
            }
        } catch (IOException | InterruptedException e) {
            Console.logError("Error al ejecutar comando en Ubuntu", e.getMessage());
            return new CreateMessage("Error al crear ws en Ubuntu: " + e.getMessage(), false);
        }

        //return new CreateMessage("Guardado Sucess", true);
    }


    public LoginMesage loginMesage(DUsuarios dUsuarios) {
        Console.logInfo("entró al login", "va a buscar el código en base de datos");
        String msg = "";
        Usuarios usuarios = usuariosRepository.findByCodigoUv(dUsuarios.getCodigoUv());
        Console.logInfo("repository", "datos" + usuarios);
        if (usuarios != null) {
            String password = dUsuarios.getPassword();
            Console.logInfo("password de la base de datos:" + password, "la contraseña ");
            String encodedPassword = usuarios.getPassword();
            Console.logInfo("passwordencoder:" + encodedPassword, "la contraseña encriptada");
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            Console.logInfo("isPwdRight:" + isPwdRight, "la contraseña");
            if (password != encodedPassword) {
                Optional<Usuarios> usuariosOptional = usuariosRepository.findOneByCodigoUvAndPassword(dUsuarios.getCodigoUv(), password);
                if (usuariosOptional.isPresent()) {
                    Console.logInfo("si entra", "al service");
                    if (usuarios.getRol().equals("user")) {
                        sesionService.registerSession(usuarios.getUserId());
                        Console.logInfo("si guardó sesion:", "del user");
                        return new LoginMesage("Login Success", true, usuarios);
                    } else {
                        sesionService.registerSession(usuarios.getUserId());
                        return new LoginMesage("Login Success Admin", true,usuarios);
                    }
                } else {
                    return new LoginMesage("Login Failed", false, usuarios);
                    }
                } else {

                 return new LoginMesage("password Not Match", false,usuarios);
                    }
                }else{
                    return new LoginMesage("Usuario not exits", false, usuarios);
                }

            }
        }