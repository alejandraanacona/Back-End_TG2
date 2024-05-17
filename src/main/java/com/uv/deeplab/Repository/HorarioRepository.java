package com.uv.deeplab.Repository;

import com.uv.deeplab.Entities.Horario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HorarioRepository extends PagingAndSortingRepository<Horario, Long> {

    public List<Horario> findByFecha(Date fecha);
    List<Horario> findByUserId(Long userId);


}
