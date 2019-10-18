package md.vnastasi.cloud.endpoint;

import md.vnastasi.cloud.endpoint.model.Station;
import md.vnastasi.cloud.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class StationsEndpoint {

    private final StationService stationService;

    public StationsEndpoint(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/")
    public Mono<ResponseEntity<List<Station>>> getStations() {
        return stationService.getStations().map(ResponseEntity::ok);
    }
}
