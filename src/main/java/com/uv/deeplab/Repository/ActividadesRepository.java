package com.uv.deeplab.Repository;

import com.uv.deeplab.Entities.Actividades;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadesRepository extends PagingAndSortingRepository<Actividades, Long> {
}
