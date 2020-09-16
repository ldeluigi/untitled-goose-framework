package untitled.goose.framework.model.actions

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock._
import untitled.goose.framework.model.entities.Dice
import untitled.goose.framework.model.events.consumable.DiceRollEvent

class RollDiceTest extends AnyFlatSpec with Matchers {

  behavior of "RollDiceTest"

  it should "fire proper event in execute" in {
    val event = RollDice("roll Dice", new Dice[Int] {
      override def name: String = "Mock dice"

      override def roll: Int = 5
    }, 1).trigger(default.currentState)

    event.isInstanceOf[DiceRollEvent[_]] should be(true)
    event.asInstanceOf[DiceRollEvent[_]].result should contain theSameElementsAs Seq(5)
  }


}
