package com.uv.deeplab.Repository;

import com.uv.deeplab.Entities.Programas;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramasRepository extends PagingAndSortingRepository<Programas, Long> {

@Query ("SELECT p " + " FROM Programas p" + " WHERE (p.userId=:userId)")
    Programas findPathById(Long userId);
}
