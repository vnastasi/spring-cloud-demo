package md.vnastasi.cloud.endpoint;

import md.vnastasi.cloud.endpoint.model.Coordinates;
import md.vnastasi.cloud.endpoint.model.Station;
import md.vnastasi.cloud.service.StationService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;

@RestController
public class StationsEndpoint {

    private static final String CACHE_CONTROL = CacheControl.maxAge(1, TimeUnit.HOURS).cachePrivate().noTransform().getHeaderValue();

    private final StationService stationService;

    public StationsEndpoint(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/")
    public ResponseEntity<Flux<Station>> getStations() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL)
                .body(stationService.getStations());
    }

    @GetMapping("/nearby")
    public ResponseEntity<Flux<Station>> getNearbyStations(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("limit") int limit
    ) {
        Coordinates coordinates = Coordinates.builder().latitude(latitude).longitude(longitude).build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL)
                .body(stationService.getNearbyStations(coordinates, limit));
    }
}
