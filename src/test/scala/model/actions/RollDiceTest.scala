package model.actions

import engine.events.DiceRollEvent
import engine.events.root.GameEvent
import mock.MatchMock._
import model.entities.Dice.Dice
import org.scalatest.flatspec.AnyFlatSpec

class RollDiceTest extends AnyFlatSpec {

  behavior of "RollDiceTest"

  it should "fire proper event in execute" in {
    RollDice(new Dice[Int] {
      override def name: String = "Mock dice"

      override def roll: Int = 5
    }).execute((event: GameEvent) => assert(event.isInstanceOf[DiceRollEvent[_]] && event.asInstanceOf[DiceRollEvent[_]].result == 5), default.currentState)
  }

}
