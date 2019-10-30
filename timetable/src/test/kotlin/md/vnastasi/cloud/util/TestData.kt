package md.vnastasi.cloud.util

import md.vnastasi.cloud.endpoint.model.*
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

const val UIC_CODE = "34567"

fun createDeparture(): Departure =
    Departure(
        destination = "Amsterdam Centraal",
        plannedDeparture = "2019-10-25T21:02:00+0200".asOffsetDateTime(),
        actualDeparture = "2019-10-25T21:07:00+0200".asOffsetDateTime(),
        plannedTrack = "1",
        unit = createTransportationUnit(),
        routeStations = listOf(createRouteStation()),
        status = DepartureStatus.DEPARTED,
        cancelled = false,
        messages = listOf(createMessage())
    )

fun createArrival(): Arrival =
    Arrival(
        origin = "Amsterdam Centraal",
        plannedArrival = "2019-10-25T21:02:00+0200".asOffsetDateTime(),
        actualArrival = "2019-10-25T21:07:00+0200".asOffsetDateTime(),
        plannedTrack = "1a",
        actualTrack = "1b",
        unit = createTransportationUnit(),
        cancelled = false,
        messages = listOf(createMessage())
    )

fun createTransportationUnit(): TransportationUnit =
    TransportationUnit(
        number = "NS 0001",
        operator = "NS",
        category = Category("IC", "Intercity"),
        type = TransportationType.TRAIN
    )

fun createRouteStation(): RouteStation =
    RouteStation("0001", "Amsterdam Sloterdijk")

fun createMessage(): Message =
    Message("A sample message", MessageType.INFO)

fun String.asOffsetDateTime(): OffsetDateTime =
    OffsetDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"))