package md.vnastasi.cloud.endpoint.model;

public record DistanceAwareStation(
        Station station,
        double distance
) {
}
