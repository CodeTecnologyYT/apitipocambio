package pe.bvva.pruebatecnica.apitipocambio.config;

import pe.bvva.pruebatecnica.apitipocambio.converts.TipoCambioConvert;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConvertConfig {

    @Bean
    public TipoCambioConvert getPhoneConvert(){
        return new TipoCambioConvert();
    }

}
