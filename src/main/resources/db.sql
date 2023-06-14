create table usuarios
(
    user_id                       bigint primary key   not null,
    codigo_uv                    varchar(50)    not null,
    nombre                       varchar(50)    not null,
    apellido                     varchar(50)    not null,
    email                        varchar(50)    not null,
    numero_identi                varchar(50)    not null,
    programa_academico           varchar(50)    not null,
    password                     varchar(50)    not null
    );

CREATE SEQUENCE if not exists usuarios_id_seq START WITH 1 INCREMENT BY 1;


create table actividades
(
    act_id                       bigint primary key   not null,
    config_defecto               varchar(50)    not null,
    act_laser                    varchar(50)    not null,
    act_camara                   varchar(50)    not null,
    act_odometria                varchar(50)    not null,
    act_mode                     varchar(50)    not null
    );
CREATE SEQUENCE if not exists actividades_id_seq START WITH 1 INCREMENT BY 1;

create table parametros
(
    parameter_id                       bigint primary key   not null,
    act_laser                    varchar(50)    not null,
    act_camara                   varchar(50)    not null,
    act_odometria                varchar(50)    not null,
    act_mode                     varchar(50)    not null
);
CREATE SEQUENCE if not exists parametros_id_seq START WITH 1 INCREMENT BY 1;


create table programas
(
    prog_id                      bigint primary key   not null,
    nombre_prog                  varchar(50)    not null,
    tipo_programa                varchar(50)    not null,
    directorio                   varchar(50)    not null,
    nombre_arch                  varchar(50)    not null
    cont_prog                    BYTEA
    );

CREATE SEQUENCE if not exists programas_id_seq START WITH 1 INCREMENT BY 1;

create table  sesion
(
    sesion_id                   bigint primary key   not null,
    fecha                       timestamp      not null,
    hora_inicio                 time           not null,
    hora_fin                    time           not null,
    config_user                 varchar(50)    not null
    );

CREATE SEQUENCE if not exists sesion_id_seq START WITH 1 INCREMENT BY 1;

create table videos
(
    videoId                   bigint primary key   not null,
    fechaCap                  timestamp      not null,
    horaCap                   time           not null,
    dataVideo                 BYTEA           not null,
    );

CREATE SEQUENCE if not exists videos_id_seq START WITH 1 INCREMENT BY 1;
