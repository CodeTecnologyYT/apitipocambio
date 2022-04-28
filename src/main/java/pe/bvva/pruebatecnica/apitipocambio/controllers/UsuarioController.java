/*
 * @(#)UsuarioController.java
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
package pe.bvva.pruebatecnica.apitipocambio.controllers;

import io.reactivex.rxjava3.core.Single;
import lombok.AllArgsConstructor;
import pe.bvva.pruebatecnica.apitipocambio.converts.UsuarioConvert;
import pe.bvva.pruebatecnica.apitipocambio.models.entities.UsuarioEntity;
import pe.bvva.pruebatecnica.apitipocambio.models.requests.LoginRequest;
import pe.bvva.pruebatecnica.apitipocambio.models.requests.UsuarioRequest;
import pe.bvva.pruebatecnica.apitipocambio.models.responses.LoginResponse;
import pe.bvva.pruebatecnica.apitipocambio.services.UsuarioService;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UsuarioController.
 *
 * @author Bryan Rosas Quispe.
 * @version 1.0.0, 28-04-2022
 */
@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioConvert usuarioConvert;

    @PostMapping("/login")
    public Single<ResponseEntity> loginRequest(@RequestBody LoginRequest loginRequest){
        return usuarioService.login(loginRequest).map(responseLogin -> ResponseEntity.ok().body(responseLogin));
    }

    @PostMapping("/signup")
    public Single<ResponseEntity> createUser(@RequestBody UsuarioRequest usuarioRequest) {
        UsuarioEntity usuarioEntity = usuarioConvert.fromRequest(usuarioRequest);
        return usuarioService.createUser(usuarioRequest.getIdRol(),usuarioEntity)
            .map( usuario -> usuarioConvert.fromEntity(usuario))
                   .map(response -> ResponseEntity.created(
                       URI.create("/usuario/" + response.getId())).body(response));
    }
}