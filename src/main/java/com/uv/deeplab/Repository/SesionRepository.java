package com.uv.deeplab.Repository;

import com.uv.deeplab.Entities.Sesion;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends PagingAndSortingRepository<Sesion, Long> {
}
