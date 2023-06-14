package com.uv.deeplab.Repository;

import com.uv.deeplab.Entities.Usuarios;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends PagingAndSortingRepository<Usuarios, Long> {

    public Usuarios findByCodigoUv(String codigoUv);
    public Usuarios save(Usuarios usuarios);
    Optional<Usuarios> findOneByCodigoUvAndPassword(String codigoUv, String password);

}
