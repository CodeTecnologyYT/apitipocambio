/*
 * @(#)TipoCambioController.java
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
import pe.bvva.pruebatecnica.apitipocambio.converts.TipoCambioConvert;
import pe.bvva.pruebatecnica.apitipocambio.models.entities.TipoCambioEntity;
import pe.bvva.pruebatecnica.apitipocambio.models.requests.ConversionCambioRequest;
import pe.bvva.pruebatecnica.apitipocambio.models.requests.TipoCambioRequest;
import pe.bvva.pruebatecnica.apitipocambio.services.TipoCambioService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TipoCambioController.
 *
 * @author Bryan Rosas Quispe.
 * @version 1.0.0, 27-04-2022
 */
@RestController
@RequestMapping("/tipoCambio")
@AllArgsConstructor
public class TipoCambioController {

    private final TipoCambioService tipoCambioService;
    private final TipoCambioConvert tipoCambioConvert;

    @PostMapping("/")
    public Single<ResponseEntity> createTipoCambio(@RequestBody TipoCambioRequest tipoCambioRequest){
        TipoCambioEntity tipoCambioEntityConvert = tipoCambioConvert.fromRequest(tipoCambioRequest);
        return tipoCambioService.createTipoDeCambio(tipoCambioEntityConvert)
            .map( tipoCambioEntity -> tipoCambioConvert.fromEntity(tipoCambioEntity))
            .map(tipoCambio ->
                ResponseEntity.created(URI.create("/tipocambio/" + tipoCambio.getId())).body(tipoCambio)
        );
    }

    @PutMapping("/")
    public Single<ResponseEntity> updateTipoCambio(@RequestBody TipoCambioRequest tipoCambioRequest){
        TipoCambioEntity tipoCambioEntityConvert = tipoCambioConvert.fromRequest(tipoCambioRequest);
        return tipoCambioService.updateTipoDeCambio(tipoCambioEntityConvert)
            .map( tipoCambioEntity -> tipoCambioConvert.fromEntity(tipoCambioEntity))
            .map(tipoCambio ->
                     ResponseEntity.created(URI.create("/tipocambio/" + tipoCambio.getId())).body(tipoCambio)
                );
    }

    @PostMapping("/{idMonedaOrigen}/{idMonedaDestino}")
    public Single<ResponseEntity> conversionTipoCambio(@PathVariable("idMonedaOrigen") String idOrigen,
        @PathVariable("idMonedaDestino") String idDestino,
        @RequestBody ConversionCambioRequest tipoCambioRequest){
        return tipoCambioService.conversionTipoDeCambio(idOrigen,idDestino,tipoCambioRequest)
            .map(conversionCambioResponse ->
                     ResponseEntity.ok().body(conversionCambioResponse)
                );
    }
}