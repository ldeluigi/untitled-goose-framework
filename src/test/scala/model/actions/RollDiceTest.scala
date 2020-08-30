package model.actions

import engine.events.consumable.DiceRollEvent
import mock.MatchMock._
import model.entities.Dice.Dice
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RollDiceTest extends AnyFlatSpec with Matchers {

  behavior of "RollDiceTest"

  it should "fire proper event in execute" in {
    val event = RollDice(new Dice[Int] {
      override def name: String = "Mock dice"

      override def roll: Int = 5
    }).trigger(default.currentState)

    (event.isInstanceOf[DiceRollEvent[_]] && event.asInstanceOf[DiceRollEvent[_]].result == Seq(5)) should be(true)
  }


}
