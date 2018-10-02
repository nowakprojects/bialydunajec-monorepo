package org.bialydunajec.ddd.domain.base.persistence

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
abstract class AuditableEntity(
        @CreatedDate
        private val createdDate: Instant = Instant.now(),

        @CreatedBy
        private var createdBy: AggregateId? = null,

        @LastModifiedDate
        private var lastModifiedDate: Instant? = null,

        @LastModifiedBy
        private var lastModifiedBy: AggregateId? = null
) : Auditable<AggregateId, Instant>, Versioned {

    @Version
    private var version: Long = 1

    override fun getCreatedDate() = createdDate

    override fun getCreatedBy() = createdBy

    override fun getLastModifiedDate() = lastModifiedDate

    override fun getLastModifiedBy() = lastModifiedBy

    override fun getVersion() = version
}