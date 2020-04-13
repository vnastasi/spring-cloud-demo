package md.vnastasi.cloud.service.impl;

import lombok.NonNull;
import md.vnastasi.cloud.endpoint.model.Coordinates;
import org.springframework.stereotype.Component;

import static java.lang.Math.*;

@Component
class DistanceCalculator {

    private static final double R = 6371e3;

    @SuppressWarnings("NonAsciiCharacters")
    @NonNull
    double calculate(@NonNull Coordinates referencePoint, @NonNull Coordinates targetPoint) {
        double lat1 = referencePoint.getLatitude();
        double lat2 = targetPoint.getLatitude();
        double lon1 = referencePoint.getLongitude();
        double lon2 = targetPoint.getLongitude();

        double φ1 = toRadians(lat1);
        double φ2 = toRadians(lat2);
        double Δφ = toRadians(lat2 - lat1);
        double Δλ = toRadians(lon2 - lon1);

        double a = pow(sin(Δφ / 2), 2) + (cos(φ1) * cos(φ2) * pow(sin(Δλ / 2), 2));
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));

        return R * c;
    }
}
