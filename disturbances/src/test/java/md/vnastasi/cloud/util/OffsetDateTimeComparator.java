package md.vnastasi.cloud.util;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;

public class OffsetDateTimeComparator implements Comparator<OffsetDateTime> {

    @Override
    public int compare(OffsetDateTime o1, OffsetDateTime o2) {
        int result = o1.atZoneSameInstant(ZoneOffset.UTC).compareTo(o2.atZoneSameInstant(ZoneOffset.UTC));
        System.err.printf("Now comparing <%s> to <%s>: %d%n", o1, o2, result);
        return result;
    }
}
