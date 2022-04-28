package pe.bvva.pruebatecnica.apitipocambio.config;

import pe.bvva.pruebatecnica.apitipocambio.converts.TipoCambioConvert;
import pe.bvva.pruebatecnica.apitipocambio.converts.UsuarioConvert;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConvertConfig {

    @Bean
    public TipoCambioConvert getPhoneConvert(){
        return new TipoCambioConvert();
    }

    @Bean
    public UsuarioConvert getUsuarioConvert(){
        return new UsuarioConvert();
    }

}
