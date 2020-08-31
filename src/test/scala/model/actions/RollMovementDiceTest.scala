package model.actions

import mock.MatchMock.default
import model.entities.Dice.MovementDice
import model.events.consumable.MovementDiceRollEvent
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RollMovementDiceTest extends AnyFlatSpec with Matchers {

  behavior of "RollMovementDiceTest"

  it should "fire proper event in execute" in {
    val event = RollMovementDice(new MovementDice {
      override def name: String = ""

      override def roll: Int = 5
    }).trigger(default.currentState)

    event.isInstanceOf[MovementDiceRollEvent] && event.asInstanceOf[MovementDiceRollEvent].result == Seq(5) should be(true)
  }


}
