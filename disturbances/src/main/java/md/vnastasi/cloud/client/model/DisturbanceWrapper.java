package md.vnastasi.cloud.client.model;

public sealed interface DisturbanceWrapper permits CalamityWrapper, DisruptionWrapper {

    String id();

    DisturbanceTypeWrapper type();

    String title();

    boolean isActive();
}
