/*
 * @(#)TipoCambioRequest.java
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
package pe.bvva.pruebatecnica.apitipocambio.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

/**
 * TipoCambioRequest.
 *
 * @author Bryan Rosas Quispe.
 * @version 1.0.0, 27-04-2022
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoCambioRequest {
    private String id;
    private String idMonedaEntrada;
    private String idMonedaSalida;
    private Double valor;
    private boolean esActivo;
    private Date periodo;
}