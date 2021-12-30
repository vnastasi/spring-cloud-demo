package md.vnastasi.cloud.endpoint;

import md.vnastasi.cloud.endpoint.model.Disruption;
import md.vnastasi.cloud.endpoint.model.DisturbanceType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class DisturbanceEndpoint {

    @GetMapping("/disruption")
    public ResponseEntity<Flux<Disruption>> getDisruptions(
            @RequestParam(value = "types", required = false) List<DisturbanceType> types
    ) {
        return ResponseEntity.ok(Flux.empty());
    }
}
