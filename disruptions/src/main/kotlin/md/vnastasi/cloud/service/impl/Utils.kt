package md.vnastasi.cloud.service.impl

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

fun epochStart(): OffsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneOffset.UTC)

fun epochEnd(): OffsetDateTime = OffsetDateTime.MAX
