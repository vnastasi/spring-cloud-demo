package md.vnastasi.cloud.util;

import md.vnastasi.cloud.client.model.*;
import md.vnastasi.cloud.endpoint.model.*;

import java.util.List;

import static md.vnastasi.cloud.util.Strings.asOffsetDatetime;

public final class TestData {

    public static final CalamityWrapper CALAMITY = new CalamityWrapper(
            "df93b6d7-1fba-462d-8578-cf4b90ed7870",
            DisturbanceTypeWrapper.CALAMITY,
            "Tijdelijk minder treinen",
            "De komende periode rijden we tijdelijk minder treinen in de spits. Ook valt  incidenteel een nachttrein in het weekend uit. Het reguliere nachtnet, inclusief de verbinding naar Schiphol, blijft ongewijzigd. Plan je reis vooraf in de reisplanner, deze wordt per dag bijgewerkt.",
            asOffsetDatetime("2021-12-27T22:34:00+0100"),
            "https://www.ns.nl/reisinformatie/calamiteiten/tijdelijk-minder-treinen-in-de-spits.html",
            true
    );

    public static final DisruptionWrapper DISRUPTION = new DisruptionWrapper(
            "6014080",
            DisturbanceTypeWrapper.DISRUPTION,
            "Amsterdam-Rotterdam-Brussel (HSL)",
            true,
            asOffsetDatetime("2021-12-28T10:31:00+0100"),
            asOffsetDatetime("2022-01-09T22:00:00+0100"),
            List.of(
                    new TimespanWrapper(
                            asOffsetDatetime("2021-12-28T10:31:00+0100"),
                            asOffsetDatetime("2022-01-09T22:00:00+0100"),
                            new LabelWrapper("Tussen Amsterdam Centraal en Brussel-Zuid Midi is voldoende afstand houden in de trein onmogelijk door grote drukte."),
                            new LabelWrapper("Grote drukte"),
                            null,
                            null,
                            List.of(
                                    "We raden af, om met de trein naar België te reizen, tenzij het echt noodzakelijk is.."
                            )
                    )
            )
    );

    public static final DisruptionWrapper MAINTENANCE = new DisruptionWrapper(
            "7001734",
            DisturbanceTypeWrapper.MAINTENANCE,
            "Eindhoven - Sittard",
            false,
            asOffsetDatetime("2022-01-01T22:20:00+0100"),
            asOffsetDatetime("2022-01-07T02:00:00+0100"),
            List.of(
                    new TimespanWrapper(
                            asOffsetDatetime("2022-01-01T22:20:00+0100"),
                            asOffsetDatetime("2022-01-02T02:00:00+0100"),
                            new LabelWrapper("Door werkzaamheden: tussen Weert en Sittard rijden er bussen."),
                            new LabelWrapper("Werkzaamheden"),
                            new LabelWrapper("De extra reistijd kan oplopen tot 30 minuten."),
                            new LabelWrapper("Er rijden snelbussen."),
                            List.of()
                    ),
                    new TimespanWrapper(
                            asOffsetDatetime("2022-01-02T22:20:00+0100"),
                            asOffsetDatetime("2022-01-03T02:00:00+0100"),
                            new LabelWrapper("Door werkzaamheden: tussen Weert en Sittard rijden er bussen."),
                            new LabelWrapper("Werkzaamheden"),
                            new LabelWrapper("De extra reistijd kan oplopen tot 30 minuten."),
                            new LabelWrapper("Er rijden snelbussen."),
                            List.of()
                    )
            )
    );

    public static final Disruption OUT_DISRUPTION = new Disruption(
            "6014080",
            "Amsterdam-Rotterdam-Brussel (HSL)",
            DisturbanceType.DISRUPTION,
            new Period(
                    asOffsetDatetime("2021-12-28T10:31:00+0100"),
                    asOffsetDatetime("2022-01-09T22:00:00+0100")
            ),
            List.of(
                    new Interval(
                            new Period(
                                    asOffsetDatetime("2021-12-28T10:31:00+0100"),
                                    asOffsetDatetime("2022-01-09T22:00:00+0100")
                            ),
                            "Tussen Amsterdam Centraal en Brussel-Zuid Midi is voldoende afstand houden in de trein onmogelijk door grote drukte.",
                            "Grote drukte",
                            null,
                            null,
                            List.of("We raden af, om met de trein naar België te reizen, tenzij het echt noodzakelijk is..")
                    )
            ),
            true
    );

    public static final Notification OUT_NOTIFICATION = new Notification(
            "df93b6d7-1fba-462d-8578-cf4b90ed7870",
            "Tijdelijk minder treinen",
            "De komende periode rijden we tijdelijk minder treinen in de spits. Ook valt  incidenteel een nachttrein in het weekend uit. Het reguliere nachtnet, inclusief de verbinding naar Schiphol, blijft ongewijzigd. Plan je reis vooraf in de reisplanner, deze wordt per dag bijgewerkt.",
            asOffsetDatetime("2021-12-27T22:34:00+0100"),
            "https://www.ns.nl/reisinformatie/calamiteiten/tijdelijk-minder-treinen-in-de-spits.html"
    );

    private TestData() {
    }
}
