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
    double calculate(@NonNull final Coordinates referencePoint, @NonNull final Coordinates targetPoint) {
        var lat1 = referencePoint.getLatitude();
        var lat2 = targetPoint.getLatitude();
        var lon1 = referencePoint.getLongitude();
        var lon2 = targetPoint.getLongitude();

        var φ1 = toRadians(lat1);
        var φ2 = toRadians(lat2);
        var Δφ = toRadians(lat2 - lat1);
        var Δλ = toRadians(lon2 - lon1);

        var a = pow(sin(Δφ / 2), 2) + (cos(φ1) * cos(φ2) * pow(sin(Δλ / 2), 2));
        var c = 2 * atan2(sqrt(a), sqrt(1 - a));

        return R * c;
    }
}
