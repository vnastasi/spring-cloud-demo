package md.vnastasi.cloud.util;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;

public final class AssertionUtils {

    public static RecursiveComparisonConfiguration createRecursiveComparisonConfiguration() {
        var config = new RecursiveComparisonConfiguration();
        config.ignoreAllOverriddenEquals();
        config.strictTypeChecking(true);
        config.registerComparatorForType(new OffsetDateTimeComparator(), OffsetDateTime.class);
        return config;
    }

    private static class OffsetDateTimeComparator implements Comparator<OffsetDateTime> {

        @Override
        public int compare(OffsetDateTime o1, OffsetDateTime o2) {
            return o1.atZoneSameInstant(ZoneOffset.UTC).compareTo(o2.atZoneSameInstant(ZoneOffset.UTC));
        }
    }

    private AssertionUtils() {
    }
}
