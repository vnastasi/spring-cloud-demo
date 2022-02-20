package md.vnastasi.cloud.endpoint;

import md.vnastasi.cloud.endpoint.model.Disruption;
import md.vnastasi.cloud.endpoint.model.DisturbanceType;
import md.vnastasi.cloud.service.DisruptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@RestController
public class DisruptionEndpoint {

    private final DisruptionService disruptionService;

    public DisruptionEndpoint(DisruptionService disruptionService) {
        this.disruptionService = disruptionService;
    }

    @GetMapping("/disruption")
    public ResponseEntity<Flux<Disruption>> getDisruptions(
            @RequestParam(value = "types", required = false) Optional<List<DisturbanceType>> types
    ) {
        return ResponseEntity.ok(disruptionService.getDisruptions(types.orElse(List.of())));
    }
}
