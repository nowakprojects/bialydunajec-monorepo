package org.bialydunajec.ddd.domain.sharedkernel.valueobject.time

import org.bialydunajec.ddd.domain.base.exception.BusinessRuleViolationException
import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import org.bialydunajec.ddd.domain.extensions.isBetween
import org.bialydunajec.ddd.domain.sharedkernel.exception.SharedKernelDomainErrorCode
import java.time.ZonedDateTime
import javax.persistence.Embeddable

@Embeddable
class ZonedDateTimeRange(
        val start: ZonedDateTime,
        val end: ZonedDateTime
) : ValueObject {

    init {
        if (end.isBefore(start)) {
            throw BusinessRuleViolationException.of(SharedKernelDomainErrorCode.END_DATE_CANNOT_BE_BEFORE_START_DATE)
        }
    }

    fun getStartDate() = start
    fun getStartDateTime() = start.toOffsetDateTime()?.toOffsetTime()
    fun getEndDate() = end
    fun getEndDateTime() = end.toOffsetDateTime()?.toOffsetTime()

    fun overlapWith(zonedDateTimeRange: ZonedDateTimeRange) =
            start.isBetween(zonedDateTimeRange.start, zonedDateTimeRange.end)
                    || end.isBetween(zonedDateTimeRange.start, zonedDateTimeRange.end)
                    || includes(zonedDateTimeRange)

    fun includes(zonedDateTimeRange: ZonedDateTimeRange) =
            start.isBefore(zonedDateTimeRange.start) && end.isAfter(zonedDateTimeRange.end)


}