package md.vnastasi.cloud.client.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CalamityWrapper.class, name = "CALAMITY"),
        @JsonSubTypes.Type(value = DisruptionWrapper.class, names = {"MAINTENANCE", "DISRUPTION"}),
})
public sealed interface DisturbanceWrapper permits CalamityWrapper, DisruptionWrapper {

    String id();

    DisturbanceTypeWrapper type();

    String title();

    boolean isActive();
}
