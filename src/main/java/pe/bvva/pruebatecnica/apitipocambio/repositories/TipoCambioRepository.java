/*
 * @(#)TipoCambioRepository.java
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
package pe.bvva.pruebatecnica.apitipocambio.repositories;

import pe.bvva.pruebatecnica.apitipocambio.models.entities.TipoCambioEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * TipoCambioRepository.
 *
 * @author Bryan Rosas Quispe.
 * @version 1.0.0, 27-04-2022
 */
@Repository
public interface TipoCambioRepository extends JpaRepository<TipoCambioEntity,String>{
    @Query("select u from TipoCambioEntity u where u.monedaEntrada.id =:idMonedaEntrada and u.monedaSalida.id =:idMonedaSalida")
    Optional<TipoCambioEntity> findByIdTipoCambio(@Param("idMonedaEntrada") String idMonedaEntrada,
        @Param("idMonedaSalida")String idMonedaSalida);

    @Query(value = "select iu from TipoCambioEntity iu where ( :descripcionOrigen is null " +
                       "or iu.monedaEntrada.descripcion like %:descripcionOrigen% ) " +
                       "and ( :descripcionDestino is null " +
                       "or iu.monedaSalida.descripcion like %:descripcionDestino% )")
    Page<TipoCambioEntity> filterTipoCambio(@Param("descripcionOrigen") String descripcionOrigen,
        @Param("descripcionDestino")String descripcionDestino, Pageable page);
}