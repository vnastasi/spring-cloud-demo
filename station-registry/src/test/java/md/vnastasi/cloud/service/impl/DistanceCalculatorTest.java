package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.endpoint.model.Coordinates;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceCalculatorTest {

    private final DistanceCalculator calculator = new DistanceCalculator();

    @Test
    void testDistanceCalculator() {
        var reference = Coordinates.builder().latitude(37.742995).longitude(-25.671291).build();
        var target = Coordinates.builder().latitude(51.4433326721191).longitude(5.48138904571533).build();

        var distance = calculator.calculate(reference, target);
        assertThat(distance).isEqualTo(2866596.7871, Offset.offset(0.0001));
    }
}
