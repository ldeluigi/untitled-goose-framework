package model.actions

import engine.events.MovementDiceRollEvent
import engine.events.root.GameEvent
import mock.MatchMock.default
import model.entities.Dice.MovementDice
import org.scalatest.flatspec.AnyFlatSpec

class RollMovementDiceTest extends AnyFlatSpec {

  behavior of "RollMovementDiceTest"

  // TODO fix this test that doesn't work
  /*
  it should "fire proper event in execute" in {
    RollMovementDice(new MovementDice {
      override def name: String = ""

      override def roll: Int = 5
    }).execute((event: GameEvent) => assert(event.isInstanceOf[MovementDiceRollEvent] && event.asInstanceOf[MovementDiceRollEvent].result == Array(5)), default.currentState)
  }
  */

}
