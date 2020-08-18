package model.actions

import engine.events.GameEvent
import engine.events.consumable.MovementDiceRollEvent
import mock.MatchMock.default
import model.entities.Dice.MovementDice
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RollMovementDiceTest extends AnyFlatSpec with Matchers {

  behavior of "RollMovementDiceTest"

  it should "fire proper event in execute" in {
    RollMovementDice(new MovementDice {
      override def name: String = ""

      override def roll: Int = 5
    }).execute((event: GameEvent) =>
      event.isInstanceOf[MovementDiceRollEvent] && event.asInstanceOf[MovementDiceRollEvent].result == Seq(5) should be(true)
      , default.currentState)
  }


}
