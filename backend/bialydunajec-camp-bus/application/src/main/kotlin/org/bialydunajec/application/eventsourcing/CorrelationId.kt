package org.bialydunajec.application.eventsourcing

import org.bialydunajec.eventsourcing.domain.Identifier

class CorrelationId(id: String) : Identifier(id) {

    companion object {
        fun from(id: Identifier) = CorrelationId(id.toString())
    }

    override fun toString() = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CorrelationId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
