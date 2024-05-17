package com.uv.deeplab.Repository;

import com.uv.deeplab.Entities.Parametros;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametrosRepository extends PagingAndSortingRepository<Parametros, Long> {
}
