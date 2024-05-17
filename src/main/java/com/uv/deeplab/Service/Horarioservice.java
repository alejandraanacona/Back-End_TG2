package com.uv.deeplab.Service;

import com.uv.deeplab.Entities.Horario;

import com.uv.deeplab.Repository.HorarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class Horarioservice {

    private final HorarioRepository horarioRepository;


    public HorarioMessage reservarHorario(Horario horario){


        // Verificar si el horario especificado en la sesión está disponible
        if (horarioDisponible(horario, obtenerHorariosOcupados(horario))) {
            // Si el horario está disponible, realizar la reserva
            realizarReserva(horario);
            return new HorarioMessage("El horario ha sido reservado exitosamente", true);
        } else {
            // Si el horario no está disponible, devolver un mensaje de error
            return new HorarioMessage("El horario seleccionado ya está ocupado", false);
        }
    }

    private List<Horario> obtenerHorariosOcupados(Horario horario) {
        return horarioRepository.findByFecha(horario.getFecha());
    }

    private boolean horarioDisponible(Horario horario, List<Horario> sesionesOcupadas) {
        Date nuevaHoraInicio = horario.getHoraInicio();
        Date nuevaHoraFin = horario.getHoraFin();

        for (Horario horarioExistente : sesionesOcupadas) {
            Date existenteHoraInicio = horarioExistente.getHoraInicio();
            Date existenteHoraFin = horarioExistente.getHoraFin();

            // Verificar si hay solapamiento entre la nueva sesión y la sesión existente
            if (nuevaHoraInicio.before(existenteHoraFin) && nuevaHoraFin.after(existenteHoraInicio)) {
                return false; // Hay solapamiento, el horario no está disponible
            }
        }

        return true; // No hay solapamiento, el horario está disponible
    }

    private void realizarReserva(Horario  horario) {
        horarioRepository.save(horario);
    }

    private List<Horario> listaHorarios() {
        return (List<Horario>) horarioRepository.findAll();
    }

    public List<Horario> obtenerHorario (Long userId){
        return horarioRepository.findByUserId(userId);
    }
}
