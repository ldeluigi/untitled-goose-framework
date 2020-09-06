package untitled.goose.framework.model.actions

import untitled.goose.framework.mock.MatchMock._
import untitled.goose.framework.model.entities.Dice.Dice
import untitled.goose.framework.model.events.consumable.DiceRollEvent
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RollDiceTest extends AnyFlatSpec with Matchers {

  behavior of "RollDiceTest"

  it should "fire proper event in execute" in {
    val event = RollDice("roll Dice", new Dice[Int] {
      override def name: String = "Mock dice"

      override def roll: Int = 5
    }, 1).trigger(default.currentState)

    event.isInstanceOf[DiceRollEvent[_]] should be
    event.asInstanceOf[DiceRollEvent[_]].result should contain theSameElementsAs Seq(5)
  }


}
