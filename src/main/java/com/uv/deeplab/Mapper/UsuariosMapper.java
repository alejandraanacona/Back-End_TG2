package com.uv.deeplab.Mapper;

import com.uv.deeplab.Dto.DUsuarios;
import com.uv.deeplab.Entities.Usuarios;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mappings;

import javax.validation.Valid;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, uses = { UsuariosMapper.class })

public interface UsuariosMapper {

    @Mappings({

    })
    Usuarios fromDto(@Valid DUsuarios dto);

    DUsuarios toDto(Usuarios entity);
}

