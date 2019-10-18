package md.vnastasi.cloud.endpoint.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NameHolder {

    @NonNull
    private String shortName;

    @NonNull
    private String middleName;

    @NonNull
    private String longName;
}
