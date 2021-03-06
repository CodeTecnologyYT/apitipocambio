/*
 * @(#)MonedaRepository.java
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

import pe.bvva.pruebatecnica.apitipocambio.models.entities.MonedaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MonedaRepository.
 *
 * @author Bryan Rosas Quispe.
 * @version 1.0.0, 27-04-2022
 */
public interface MonedaRepository extends JpaRepository<MonedaEntity,String> {
}