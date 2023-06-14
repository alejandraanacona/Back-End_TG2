package com.uv.deeplab.Enum;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Getter
public enum EtipoPrograma {

    SCRIPTS("file script"),
    LAUNCH("file launch"),
    CONFIG("file config");

    private String description;

}
