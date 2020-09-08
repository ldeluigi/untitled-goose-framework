package untitled.goose.framework.model.actions

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock.default
import untitled.goose.framework.model.entities.Dice.MovementDice
import untitled.goose.framework.model.events.consumable.MovementDiceRollEvent

class RollMovementDiceTest extends AnyFlatSpec with Matchers {

  behavior of "RollMovementDiceTest"

  it should "fire proper event in execute" in {
    val event = RollMovementDice(new MovementDice {
      override def name: String = ""

      override def roll: Int = 5
    }).trigger(default.currentState)

    event.isInstanceOf[MovementDiceRollEvent] should be(true)
    event.asInstanceOf[MovementDiceRollEvent].result should contain theSameElementsAs Seq(5)
  }


}
