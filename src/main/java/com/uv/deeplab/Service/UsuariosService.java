package com.uv.deeplab.Service;

import com.uv.deeplab.Dto.DUsuarios;
import com.uv.deeplab.Entities.Usuarios;

import com.uv.deeplab.Repository.UsuariosRepository;
import com.uv.deeplab.config.Console;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.Random;

@Service
@Log4j2
@RequiredArgsConstructor
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;
    private final EmailService emailService;

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
        String attachment= "esto se debe borrar";

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


    public LoginMesage  loginMesage(DUsuarios dUsuarios) {
        Console.logInfo("entró al login", "va a buscar el código en base de datos");
        String msg = "";
        Usuarios usuarios = usuariosRepository.findByCodigoUv(dUsuarios.getCodigoUv());
        Console.logInfo("repository", "datos"+ usuarios);
        if (usuarios != null) {
            String password = dUsuarios.getPassword();
            Console.logInfo("password de la base de datos:"+ password, "la contraseña ");
            String encodedPassword = usuarios.getPassword();
            Console.logInfo("passwordencoder:"+ encodedPassword, "la contraseña encriptada");
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            Console.logInfo("isPwdRight:"+ isPwdRight, "la contraseña");
            if (password != encodedPassword) {
                Optional<Usuarios> usuariosOptional = usuariosRepository.findOneByCodigoUvAndPassword(dUsuarios.getCodigoUv(), password);
                if (usuariosOptional.isPresent()) {
                    Console.logInfo("si entra","al service");
                    return new LoginMesage("Login Success", true);
                } else {
                    return new LoginMesage("Login Failed", false);
                }
            } else {

                return new LoginMesage("password Not Match", false);
            }
        }else {
            return new LoginMesage("Usuario not exits", false);
        }


    }
}
