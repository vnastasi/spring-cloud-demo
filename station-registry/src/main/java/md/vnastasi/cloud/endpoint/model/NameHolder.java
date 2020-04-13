package md.vnastasi.cloud.endpoint.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NameHolder {

    @NonNull
    String shortName;

    @NonNull
    String middleName;

    @NonNull
    String longName;
}
