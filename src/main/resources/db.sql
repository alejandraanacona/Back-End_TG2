create table usuarios
(
    user_id                      bigint primary key   not null,
    codigo_uv                    varchar(50)    not null,
    nombre                       varchar(50)    not null,
    apellido                     varchar(50)    not null,
    email                        varchar(50)    not null,
    programa_academico           varchar(50)    not null,
    password                     varchar(50)    not null,
    CONSTRAINT codigo_uv_unique UNIQUE (codigo_uv)
);

CREATE SEQUENCE if not exists usuarios_id_seq START WITH 1 INCREMENT BY 1;


create table parametros
(
    param_id                     bigint         primary key   not null,
    user_id                      biginit        not null,
    param_laser                  varchar(50)    not null,
    param_camara                 varchar(50)    not null,
    param_vel_max                varchar(50)    not null,
    param_vel_ang_max            varchar(50)    not null,
    param_mode                   boolean        not null,
    FOREIGN KEY (user_id) REFERENCES usuarios(user_id)
);
CREATE SEQUENCE if not exists parametros_id_seq START WITH 1 INCREMENT BY 1;



create table programas
(
    id                      bigint primary key   not null,
    userId                   varchar(50)    not null,
    nameFolder               varchar(50)    not null,
    path                     varchar(50)    not null,
    parentId                 varchar(50)    not null
    FOREIGN KEY (user_id) REFERENCES usuarios(user_id)
    );

CREATE SEQUENCE if not exists programas_id_seq START WITH 1 INCREMENT BY 1;

create table  sesion
(
    sesion_id                   bigint primary key   not null,
    user_id                varchar(50)    not null,
    fecha                       timestamp      not null,
    hora_inicio                 time           not null,
    hora_fin                    time           not null,

    FOREIGN KEY (user_id) REFERENCES usuarios(user_id)
    );

CREATE SEQUENCE if not exists sesion_id_seq START WITH 1 INCREMENT BY 1;

create table  horario
(
    horario_id                      bigint primary key   not null,
    user_id                           varchar(50)              not null,
    fecha                              timestamp               not null,
    hora_inicio                     time                          not null,
    hora_fin                         time                         not null,

    FOREIGN KEY (user_id) REFERENCES usuarios(user_id)
);

CREATE SEQUENCE if not exists horario_id_seq START WITH 1 INCREMENT BY 1;

