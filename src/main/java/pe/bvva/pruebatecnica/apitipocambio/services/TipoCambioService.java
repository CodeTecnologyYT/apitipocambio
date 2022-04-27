/*
 * @(#)TipoCambioService.java
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
package pe.bvva.pruebatecnica.apitipocambio.services;

import io.reactivex.rxjava3.core.Single;
import lombok.AllArgsConstructor;
import pe.bvva.pruebatecnica.apitipocambio.exceptions.NoDataFoundException;
import pe.bvva.pruebatecnica.apitipocambio.models.entities.MonedaEntity;
import pe.bvva.pruebatecnica.apitipocambio.models.entities.TipoCambioEntity;
import pe.bvva.pruebatecnica.apitipocambio.repositories.MonedaRepository;
import pe.bvva.pruebatecnica.apitipocambio.repositories.TipoCambioRepository;
import java.util.Optional;
import java.util.concurrent.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * TipoCambioService.
 *
 * @author Bryan Rosas Quispe.
 * @version 1.0.0, 27-04-2022
 */
@Service
@AllArgsConstructor
public class TipoCambioService {

    private final TipoCambioRepository tipoCambioRepository;
    private final MonedaRepository monedaRepository;

    public Single<TipoCambioEntity> createTipoDeCambio(TipoCambioEntity tipoCambioEntity){
        return Single.create(singleSubscriber ->{
            Optional<MonedaEntity> monedaEntradaEncontrada =
                monedaRepository.findById(tipoCambioEntity.getMonedaEntrada().getId());
            Optional<MonedaEntity> monedaSalidaEncontrada =
                monedaRepository.findById(tipoCambioEntity.getMonedaSalida().getId());
            if(monedaEntradaEncontrada.isEmpty() || monedaSalidaEncontrada.isEmpty())
                singleSubscriber.onError(new NoDataFoundException("No se encontro el id de la moneda"));
            Optional<TipoCambioEntity> tipoCambioEncontrado =
                tipoCambioRepository.findByIdTipoCambio(tipoCambioEntity.getMonedaEntrada().getId(),
                    tipoCambioEntity.getMonedaSalida().getId());
            if(tipoCambioEncontrado.isPresent())
                singleSubscriber.onError(new NoDataFoundException("Ya existe el tipo de cambio"));

            tipoCambioEntity.setEsActivo(true);
            TipoCambioEntity tipocambioCreate = tipoCambioRepository.save(tipoCambioEntity);

            singleSubscriber.onSuccess(tipocambioCreate);
        });
    }

    public Single<TipoCambioEntity> updateTipoDeCambio(TipoCambioEntity tipoCambioEntity){
        return Single.create(singleSubscriber ->{
            Optional<MonedaEntity> monedaEntradaEncontrada =
                monedaRepository.findById(tipoCambioEntity.getMonedaEntrada().getId());
            Optional<MonedaEntity> monedaSalidaEncontrada =
                monedaRepository.findById(tipoCambioEntity.getMonedaSalida().getId());
            if(monedaEntradaEncontrada.isEmpty() || monedaSalidaEncontrada.isEmpty())
                singleSubscriber.onError(new NoDataFoundException("No se encontro el id de la moneda"));
            Optional<TipoCambioEntity> tipoCambioEncontrado =
                tipoCambioRepository.findByIdTipoCambio(tipoCambioEntity.getMonedaEntrada().getId(),
                    tipoCambioEntity.getMonedaSalida().getId());
            if(tipoCambioEncontrado.isEmpty())
                singleSubscriber.onError(new NoDataFoundException("No existe el tipo de cambio"));
            TipoCambioEntity tipocambioCreate = tipoCambioRepository.save(tipoCambioEntity);

            singleSubscriber.onSuccess(tipocambioCreate);
        });
    }
}