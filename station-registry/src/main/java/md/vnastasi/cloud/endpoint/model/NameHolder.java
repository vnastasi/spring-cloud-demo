package md.vnastasi.cloud.endpoint.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class NameHolder {

    @NonNull
    private String shortName;

    @NonNull
    private String middleName;

    @NonNull
    private String longName;
}
