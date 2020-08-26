package org.bialydunajec.registrations.domain.exception

import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRule

enum class CampRegistrationsDomainRule : DomainRule {
    ACADEMIC_MINISTRY_NOT_FOUND,
    COTTAGE_NOT_FOUND,
    CAMP_REGISTRATIONS_NOT_FOUND,
    CAMP_EDITION_NOT_FOUND,
    IN_PROGRESS_CAMP_REGISTRATIONS_NOT_FOUND,
    ACTIVE_COTTAGE_CANNOT_BE_DELETED,
    COTTAGE_WITH_CAMP_PARTICIPANTS_CANNOT_BE_DELETED {
        override fun getDescription(): String? = "Chatka nie może być usunięta, jesli są do niej zapisani obozowicze."
    },

    CAMP_EDITION_HAS_NOT_IN_PROGRESS_REGISTRATIONS,
    NO_DEFINED_ACADEMIC_MINISTRY_FOR_COTTAGE,
    CHECK_IN_TIME_CAN_NOT_BE_SPECIFIED_WITHOUT_CHECK_IN_DATE,
    CHECK_OUT_TIME_CAN_NOT_BE_SPECIFIED_WITHOUT_CHECK_OUT_DATE,

    NOT_CONFIGURED_CAMP_REGISTRATIONS_CANNOT_BE_ACTIVATED,
    IN_PROGRESS_CAMP_REGISTRATIONS_CANNOT_BE_UPDATED,
    FINISHED_CAMP_REGISTRATIONS_CANNOT_BE_UPDATED,
    FINISHED_CAMP_REGISTRATIONS_CANNOT_START,
    IN_PROGRESS_CAMP_REGISTRATIONS_CANNOT_START,

    ONLY_ONE_CAMP_REGISTRATIONS_CAN_BE_IN_PROGRESS_IN_THE_SAME_TIME {
        override fun getDescription(): String? = "Tylko jedne zapisy na obóz mogą być aktywne w tym samym czasie."
    },
    CAMP_REGISTERS_HAS_TO_HAVE_CONFIGURED_TIMER_TO_START_BY_TIMER,
    CAMP_REGISTERS_HAS_TO_NOT_BE_IN_PROGRESS_TO_START_BY_TIMER,
    CAMP_REGISTERS_HAS_ALREADY_STARTED,

    CAMP_REGISTERS_START_DATE_HAS_TO_BE_BEFORE_END_DATE,
    CAMP_REGISTERS_END_DATE_HAS_TO_BE_AFTER_START_DATE,
    CAMP_REGISTERS_END_DATE_HAS_TO_BE_IN_THE_FUTURE,
    CAMP_REGISTERS_START_DATE_HAS_TO_BE_IN_THE_FUTURE,
    ONLY_IN_PROGRESS_CAMPERS_REGISTER_CAN_BE_FINISHED,
    ONLY_CONFIGURED_TIMER_OR_IN_PROGRESS_REGISTERS_CAN_BE_SUSPENDED,
    ONLY_SUSPENDED_REGISTERS_CAN_BE_UNSUSPEND,

    NOT_CONFIGURED_COTTAGE_CANNOT_BE_ACTIVATED,
    ACTIVATED_COTTAGE_HAS_TO_HAVE_BASIC_BANK_TRANSFER_INFO,
    ACTIVATED_COTTAGE_HAS_TO_HAVE_ADDRESS,
    ACTIVATED_COTTAGE_HAS_TO_HAVE_LOGO,

    ALL_COTAGE_SPACE_VALUES_HAVE_TO_BE_POSITIVE_NUMBER,
    COTTAGE_RESERVATIONS_AMOUNT_CANNOT_BE_GRATER_THAN_FULL_CAPACITY,
    MAX_GENDER_TOTAL_CANNOT_BE_GRATER_THAN_FULL_CAPACITY,

    CAMP_PARTICIPANT_WITH_GIVEN_PESEL_IS_ALREADY_REGISTERED,
    CAMP_PARTICIPANT_REGISTRATIONS_TO_CONFIRM_MUST_EXISTS,
    CAMP_PARTICIPANT_REGISTRATIONS_TO_CANCEL_MUST_EXISTS,
    INVALID_CAMP_PARTICIPANT_REGISTRATION_VERIFICATION_CODE,
    CAMP_PARTICIPANT_REGISTRATION_ALREADY_VERIFIED,
    CAMP_PARTICIPANT_REGISTRATION_ALREADY_VERIFIED_BY_AUTHORIZED,
    CAMP_PARTICIPANT_REGISTRATION_ALREADY_CANCELLED,
    CAMP_PARTICIPANT_REGISTRATION_ALREADY_CANCELLED_BY_AUTHORIZED,
    CAMP_PARTICIPANT_REGISTRATION_ALREADY_CANCELLED_BY_DEADLINE,
    CAMP_PARTICIPANT_MUST_EXISTS_TO_UPDATE_REGISTRATION_DATA,


    SHIRT_ORDER_CAN_ONLY_BE_PLACED_ONLY_FOR_THIS_EDITION_CAMP_PARTICIPANT,
    SHIRT_ORDER_CAN_ONLY_BE_PLACED_FOR_AVAILABLE_COLOR,
    SHIRT_ORDER_CAN_ONLY_BE_PLACED_FOR_AVAILABLE_SIZE,
    SHIRT_COLOR_OPTION_CAN_NOT_BE_DUPLICATED,
    SHIRT_SIZE_OPTION_CAN_NOT_BE_DUPLICATED,
    SHIRT_TO_UPDATE_MUST_EXISTS,
    SHIRT_TO_ORDER_MUST_EXISTS,
    SHIRT_CAN_BE_ORDERED_ONLY_FOR_EXISTING_CAMP_PARTICIPANT,
    SHIRT_ORDER_TO_DELETE_MUST_EXISTS,

    WITHDRAW_AMOUNT_HAS_TO_BE_LESS_OR_EQUALS_TO_AVAILABLE_FUNDS,
    PAYMENT_AMOUNT_HAS_TO_BE_LESS_THAN_AVAILABLE_FUNDS,
    CAMP_PARTICIPATION_DOWN_PAYMENT_HAS_TO_BE_PAID_BEFORE_CAMP_PARTICIPATION_COMMITMENT,
    CAMP_PARTICIPATION_COMMITMENT_ALREADY_PAID,
    CAMP_DOWN_PAYMENT_HAS_TO_BE_ON_ACCOUNT_COMMITMENTS,
    CAMP_DOWN_PAYMENT_COMMITMENT_ALREADY_PAID,
    CAMP_BUS_HAS_TO_BE_ON_ACCOUNT_COMMITMENTS,
    CAMP_BUS_PAYMENT_ALREADY_PAID,
    DEPOSIT_IS_NOT_ALLOWED_IF_ALL_COMMITMENTS_ARE_PAID,
    DEPOSIT_IS_NOT_ALLOWED_IF_DISABLED_IN_CONFIGURATION,
    WITHDRAW_IS_NOT_ALLOWED_IF_DISABLED_IN_CONFIGURATION,
    PAYMENT_IS_NOT_ALLOWED_IF_DISABLED_IN_CONFIGURATION,
    CAMP_PARTICIPANT_COTTAGE_ACCOUNT_MUST_EXISTS_TO_MAKE_A_PAYMENT,
    CAMP_PARTICIPANT_COTTAGE_ACCOUNT_TO_CLOSE_MUST_EXISTS,


    ONE_CAMP_PARTICIPANT_CAN_RESERVE_ONLY_ONE_SEAT,
    CAMP_PARTICIPANT_CAN_RESERVE_SEAT_WHEN_RESERVATIONS_ARE_INACTIVE,

    CAMP_PARTICIPANT_TO_UNREGISTER__MUST_EXISTS;

    override fun getRuleName() = name
    override fun getDescription(): String? = null
}
