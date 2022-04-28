/*
 * @(#)TipoCambioEntity.java
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
package pe.bvva.pruebatecnica.apitipocambio.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * TipoCambioEntity.
 *
 * @author Bryan Rosas Quispe.
 * @version 1.0.0, 27-04-2022
 */
@Table(name = "TDC_TIPOCAMBIO")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoCambioEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;
    @ManyToOne
    @JoinColumn(name = "ID_MONEDAENTRADA")
    @JsonIgnoreProperties({"tipoCambioSalida","tipoCambioEntrada"})
    private MonedaEntity monedaEntrada;
    @ManyToOne
    @JoinColumn(name = "ID_MONEDASALIDA")
    @JsonIgnoreProperties({"tipoCambioSalida","tipoCambioEntrada"})
    private MonedaEntity monedaSalida;
    @Column(name = "VALOR", columnDefinition = "NUMERIC", nullable = false)
    private Double valor;
    @Column(name = "PERIODO", columnDefinition = "DATE", nullable = false)
    private Date periodo;
    @Column(name = "ACTIVO", columnDefinition = "BOOLEAN")
    private boolean esActivo;
}