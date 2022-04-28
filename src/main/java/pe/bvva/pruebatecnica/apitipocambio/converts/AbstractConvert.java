package pe.bvva.pruebatecnica.apitipocambio.converts;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractConvert<Request,Entity,Response>{

    public abstract Entity fromRequest(Request request);
    public abstract Response fromEntity(Entity entity);

    public Set<Entity> fromRequest(List<Request> requests){
        return requests.stream()
                .map(request -> fromRequest(request))
                .collect(Collectors.toSet());
    }
    public Set<Response> fromEntity(List<Entity> entities){
        return entities.stream()
            .map(entity -> fromEntity(entity))
            .collect(Collectors.toSet());
    }
}
