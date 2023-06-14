package com.uv.deeplab.Repository;

import com.uv.deeplab.Entities.Programas;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramasRepository extends PagingAndSortingRepository<Programas, Long> {
}
