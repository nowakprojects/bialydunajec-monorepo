package org.bialydunajec.campbus

import assertk.assertThat
import assertk.assertions.hasClass
import com.github.nowakprojects.kttimetraveler.test.TestClockTimeProvider
import org.bialydunajec.campbus.domain.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.time.LocalTime

object EventSourcedSeatRepositorySpecification : Spek({

    Feature("time traveling with event sourcing") {

        Scenario("events up to specific point in the past") {
            val timeProvider: TestClockTimeProvider
                    by memoized { TestClockTimeProvider.withFixedTime(LocalTime.of(10, 0)) }
            val repository: SeatRepository by memoized { InMemorySeatEventSourcedRepository(timeProvider) }
            val campBusCourseId: BusCourseId by memoized { BusCourseId() }
            val seatId: SeatId by memoized { SeatId() }
            val passengerId: PassengerId by memoized { PassengerId() }
            val initialSeat: Seat by memoized { Seat.newInstance { timeProvider.instant }.handle(SeatCommand.AddSeatForCourse(seatId, campBusCourseId)) }

            Given("current time is 10:00") {
                timeProvider.timeTravelTo(LocalTime.of(10, 0))
                println(timeProvider.instant)
            }

            And("seat is saved") {
                repository.save(initialSeat)
            }

            When("we reserve the seat at 10:10") {
                timeProvider.timeTravelTo(LocalTime.of(10, 10))
                println(timeProvider.instant)
                reserveBusSeat(repository, seatId, passengerId)
            }

            Then("if we load seat data with time set to 10:11 next reservation should fail") {
                timeProvider.timeTravelTo(LocalTime.of(10, 11))
                println(timeProvider.instant)
                assertThat(reserveBusSeat(repository, seatId, passengerId)).hasClass(SeatEvent.SeatReservationFailed::class)
            }

            And("if we load seat data with time set to 10:01 reservation should be possible") {
                timeProvider.timeTravelTo(LocalTime.of(10, 1))
                assertThat {
                    reserveBusSeat(repository, seatId, passengerId)
                }.isSuccess()
            }


        }

    }
})

private fun reserveBusSeat(repository: SeatRepository, seatId: SeatId, passengerId: PassengerId): SeatEvent {
    val seat = repository.findById(seatId)!!.handle(SeatCommand.ReserveSeat(seatId, passengerId))
    val lastEvent = seat.changes.last()
    repository.save(seat)
    return lastEvent
}

