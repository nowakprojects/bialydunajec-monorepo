package org.bialydunajec.registrations.domain.exception

import org.bialydunajec.ddd.domain.base.validation.exception.DomainRule

enum class CampRegistrationsDomainRule : DomainRule {
    ACADEMIC_MINISTRY_NOT_FOUND,
    COTTAGE_NOT_FOUND,
    CAMP_REGISTRATIONS_NOT_FOUND,
    CAMP_EDITION_NOT_FOUND,
    IN_PROGRESS_CAMP_REGISTRATIONS_NOT_FOUND,


    CAMP_EDITION_HAS_NOT_IN_PROGRESS_REGISTRATIONS,
    NO_DEFINED_ACADEMIC_MINISTRY_FOR_COTTAGE,
    CHECK_IN_TIME_CAN_NOT_BE_SPECIFIED_WITHOUT_CHECK_IN_DATE,
    CHECK_OUT_TIME_CAN_NOT_BE_SPECIFIED_WITHOUT_CHECK_OUT_DATE,

    NOT_CONFIGURED_CAMP_REGISTRATIONS_CANNOT_BE_ACTIVATED,
    IN_PROGRESS_CAMP_REGISTRATIONS_CANNOT_BE_UPDATED,
    FINISHED_CAMP_REGISTRATIONS_CANNOT_BE_UPDATED,
    FINISHED_CAMP_REGISTRATIONS_CANNOT_START,

    CAMP_REGISTERS_HAS_TO_HAVE_MIN_COTTAGES_TO_START,
    CAMP_REGISTERS_HAS_TO_HAVE_CONFIGURED_TIMER_TO_START_BY_TIMER,
    CAMP_REGISTERS_HAS_TO_NOT_BE_IN_PROGRESS_TO_START_BY_TIMER,
    CAMP_REGISTERS_HAS_ALREADY_STARTED,

    CAMP_REGISTERS_START_DATE_HAS_TO_BE_BEFORE_END_DATE,
    CAMP_REGISTERS_END_DATE_HAS_TO_BE_AFTER_START_DATE,
    CAMP_REGISTERS_END_DATE_HAS_TO_BE_IN_THE_FUTURE,
    CAMP_REGISTERS_START_DATE_HAS_TO_BE_IN_THE_FUTURE,
    ONLY_IN_PROGRESS_CAMPERS_REGISTER_CAN_BE_FINISHED,
    ONLY_CONFIGURED_TIMER_OR_IN_PROGRESS_REGISTERS_CAN_BE_SUSPENDED,
    ONLY_SUSPENDED_REGISTERS_CAN_BE_UNSUSPEND;

    override fun getName() = name

    override fun getDescription() = ""
}