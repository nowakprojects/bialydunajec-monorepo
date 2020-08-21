package org.bialydunajec.registrations.domain.academicministry

import org.bialydunajec.ddd.domain.base.valueobject.AggregateId
import javax.persistence.Embeddable

@Embeddable
class AcademicMinistryId(academicMinistryId: String) : AggregateId(academicMinistryId)