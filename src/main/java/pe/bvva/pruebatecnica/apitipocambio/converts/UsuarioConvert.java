/*
 * @(#)UsuarioConvert.java
 *
 * Copyright (c) BBVA (PERU). All rights reserved.
 *
 * All rights to this product are owned by BVVA and may only
 * be used under the terms of its associated license document. You may NOT
 * copy, modify, sublicense, or distribute this source file or portions of
 * it unless previously authorized in writing by BBVA.
 * In any event, this notice and the above copyright must always be included
 * verbatim with this file.
 */
package pe.bvva.pruebatecnica.apitipocambio.converts;

import pe.bvva.pruebatecnica.apitipocambio.models.entities.UsuarioEntity;
import pe.bvva.pruebatecnica.apitipocambio.models.requests.UsuarioRequest;
import pe.bvva.pruebatecnica.apitipocambio.models.responses.RolResponse;
import pe.bvva.pruebatecnica.apitipocambio.models.responses.UsuarioResponse;
import java.util.stream.Collectors;

/**
 * UsuarioConvert.
 *
 * @author Bryan Rosas Quispe.
 * @version 1.0.0, 28-04-2022
 */
public class UsuarioConvert extends AbstractConvert<UsuarioRequest, UsuarioEntity, UsuarioResponse> {

    @Override public UsuarioEntity fromRequest(final UsuarioRequest usuarioRequest) {
        return UsuarioEntity.builder()
            .password(usuarioRequest.getPassword())
            .username(usuarioRequest.getUsername())
            .build();
    }

    @Override public UsuarioResponse fromEntity(final UsuarioEntity usuarioEntity) {
        return UsuarioResponse.builder()
            .id(usuarioEntity.getId())
            .username(usuarioEntity.getUsername())
            .activo(usuarioEntity.getActivo())
            .rol(usuarioEntity.getUsuarioRole().stream()
                .map(usuarioRole -> RolResponse.builder()
                        .id(usuarioRole.getRol().getId())
                        .descripcion(usuarioRole.getRol().getDescripcion())
                        .build()
                    ).collect(Collectors.toList()))
            .build();
    }

}