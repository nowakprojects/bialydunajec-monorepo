package org.bialydunajec.campschedule.eventsourcing

import org.bialydunajec.ddd.domain.base.valueobject.Identifier
import java.util.*

abstract class AggregateIdentifier(
        private val aggregateId: String = defaultValue()
) : Identifier<String> {

    override fun toString() = aggregateId

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AggregateIdentifier

        if (aggregateId != other.aggregateId) return false

        return true
    }

    override fun hashCode(): Int {
        return aggregateId.hashCode()
    }

    override fun getIdentifierValue() = aggregateId

    companion object {
        fun defaultValue() = UUID.randomUUID().toString()
    }
}
