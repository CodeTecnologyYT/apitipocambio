package pe.bvva.pruebatecnica.apitipocambio.converts;

import pe.bvva.pruebatecnica.apitipocambio.models.entities.MonedaEntity;
import pe.bvva.pruebatecnica.apitipocambio.models.entities.TipoCambioEntity;
import pe.bvva.pruebatecnica.apitipocambio.models.requests.TipoCambioRequest;
import pe.bvva.pruebatecnica.apitipocambio.models.responses.TipoCambioResponse;

public class TipoCambioConvert extends AbstractConvert<TipoCambioRequest, TipoCambioEntity,TipoCambioResponse>{
    @Override
    public TipoCambioEntity fromRequest(TipoCambioRequest tipoCambioRequest) {
        return TipoCambioEntity.builder()
            .id(tipoCambioRequest.getId())
            .monedaEntrada(MonedaEntity.builder().id(tipoCambioRequest.getIdMonedaEntrada()).build())
            .monedaSalida(MonedaEntity.builder().id(tipoCambioRequest.getIdMonedaSalida()).build())
            .valor(tipoCambioRequest.getValor())
            .periodo(tipoCambioRequest.getPeriodo())
            .esActivo(tipoCambioRequest.isEsActivo())
                .build();
    }

    @Override
    public TipoCambioResponse fromEntity(TipoCambioEntity tipoCambioEntity) {
        return TipoCambioResponse.builder()
                   .id(tipoCambioEntity.getId())
                   .idMonedaEntrada(tipoCambioEntity.getMonedaEntrada().getId())
                   .idMonedaSalida(tipoCambioEntity.getMonedaSalida().getId())
                   .valor(tipoCambioEntity.getValor())
                   .periodo(tipoCambioEntity.getPeriodo())
                   .esActivo(tipoCambioEntity.isEsActivo())
            .build();
    }
}
