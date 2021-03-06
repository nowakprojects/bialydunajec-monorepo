package org.bialydunajec.ddd.domain.base.aggregate

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Identifier
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.MappedSuperclass

/**
 * Aggregate Root base class based in the Spring Data one {@link org.springframework.data.domain.AbstractAggregateRoot}.
 */
@MappedSuperclass
abstract class AggregateRoot<AggregateIdType : Identifier<*>, EventType : DomainEvent<AggregateIdType>>(
        @Column(unique = true, updatable = false)
        @EmbeddedId
        private val aggregateId: AggregateIdType,
        @kotlin.jvm.Transient
        private var domainEvents: MutableList<EventType>? = mutableListOf()
) {

    fun getAggregateId() = aggregateId

    /**
     * Registers the given event object for publication on a call to a Spring Data repository's save methods.
     */
    protected fun registerEvent(event: EventType): EventType {
        if(domainEvents== null){
            domainEvents = mutableListOf()
        }
        this.domainEvents?.add(event)
        return event
    }

    /**
     * All domain events currently captured by the aggregate.
     */
    fun domainEvents(): Collection<EventType> {
        return domainEvents?.toList() ?: emptyList()
    }

    /**
     * Clears all domain events currently held. Usually invoked by the infrastructure in place in Spring Data
     * repositories.
     */
    fun clearDomainEvents() {
        if(domainEvents== null){
            domainEvents = mutableListOf()
        }
        this.domainEvents?.clear()
    }

    override fun equals(other: Any?): Boolean = other!=null && this.hashCode() == other.hashCode()

    override fun hashCode(): Int = aggregateId.hashCode()


}
