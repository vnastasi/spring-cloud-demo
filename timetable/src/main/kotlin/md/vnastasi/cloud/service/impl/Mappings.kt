package md.vnastasi.cloud.service.impl

import md.vnastasi.cloud.client.model.*
import md.vnastasi.cloud.endpoint.model.*

fun ProductTypeWrapper.asTransportationType(): TransportationType =
    when (this) {
        ProductTypeWrapper.BIKE -> TransportationType.BIKE
        ProductTypeWrapper.BUS -> TransportationType.BUS
        ProductTypeWrapper.CAR -> TransportationType.CAR
        ProductTypeWrapper.FERRY -> TransportationType.FERRY
        ProductTypeWrapper.METRO -> TransportationType.METRO
        ProductTypeWrapper.SUBWAY -> TransportationType.SUBWAY
        ProductTypeWrapper.TAXI -> TransportationType.TAXI
        ProductTypeWrapper.TRAIN -> TransportationType.TRAIN
        ProductTypeWrapper.TRAM -> TransportationType.TRAM
        ProductTypeWrapper.WALK -> TransportationType.WALK
        else -> TransportationType.UNKNOWN
    }

fun DepartureStatusWrapper.asDepartureStatus(): DepartureStatus =
    when (this) {
        DepartureStatusWrapper.INCOMING -> DepartureStatus.EXPECTED
        DepartureStatusWrapper.ON_STATION -> DepartureStatus.BOARDING
        DepartureStatusWrapper.DEPARTED -> DepartureStatus.DEPARTED
    }

fun MessageTypeWrapper.asMessageType(): MessageType =
    when (this) {
        MessageTypeWrapper.INFO -> MessageType.INFO
        MessageTypeWrapper.WARNING -> MessageType.WARNING
    }

fun MessageWrapper.asMessage(): Message =
    Message(text = this.text, type = this.type.asMessageType())

fun RouteStationWrapper.asRoutStation(): RouteStation =
    RouteStation(code = this.uicCode, name = this.name)

fun ProductWrapper.extractCategory(): Category =
    Category(code = this.categoryCode, name = this.longCategoryName)

fun ProductWrapper.asTransportationUnit(): TransportationUnit =
    TransportationUnit(
        number = "${this.operatorCode} ${this.number}",
        type = this.type.asTransportationType(),
        operator = this.operatorName,
        category = this.extractCategory()
    )

fun DepartureWrapper.asDeparture(): Departure =
    Departure(
        destination = this.direction,
        unit = this.product.asTransportationUnit(),
        plannedDeparture = this.plannedDateTime,
        actualDeparture = this.actualDateTime,
        plannedTrack = this.plannedTrack,
        routeStations = this.routeStations.map { it.asRoutStation() }.toList(),
        status = this.departureStatus.asDepartureStatus(),
        cancelled = this.cancelled,
        messages = this.messages?.map { it.asMessage() }?.toList() ?: emptyList()
    )

fun ArrivalWrapper.asArrival(): Arrival =
    Arrival(
        origin = this.origin,
        unit = this.product.asTransportationUnit(),
        plannedArrival = this.plannedDateTime,
        actualArrival = this.actualDateTime,
        plannedTrack = this.plannedTrack,
        actualTrack = this.actualTrack,
        cancelled = this.cancelled,
        messages = this.messages?.map { it.asMessage() }?.toList() ?: emptyList()
    )
