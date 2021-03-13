package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.endpoint.model.Coordinates;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static java.lang.Math.*;

@Component
class DistanceCalculator {

    private static final double R = 6371e3;

    @SuppressWarnings("NonAsciiCharacters")
    @NonNull
    double calculate(@NonNull final Coordinates referencePoint, @NonNull final Coordinates targetPoint) {
        var lat1 = referencePoint.latitude();
        var lat2 = targetPoint.latitude();
        var lon1 = referencePoint.longitude();
        var lon2 = targetPoint.longitude();

        var φ1 = toRadians(lat1);
        var φ2 = toRadians(lat2);
        var Δφ = toRadians(lat2 - lat1);
        var Δλ = toRadians(lon2 - lon1);

        var a = pow(sin(Δφ / 2), 2) + (cos(φ1) * cos(φ2) * pow(sin(Δλ / 2), 2));
        var c = 2 * atan2(sqrt(a), sqrt(1 - a));

        return R * c;
    }
}
