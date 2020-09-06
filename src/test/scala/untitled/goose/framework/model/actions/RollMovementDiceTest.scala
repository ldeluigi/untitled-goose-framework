package untitled.goose.framework.model.actions

import untitled.goose.framework.mock.MatchMock.default
import untitled.goose.framework.model.entities.Dice.MovementDice
import untitled.goose.framework.model.events.consumable.MovementDiceRollEvent
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
