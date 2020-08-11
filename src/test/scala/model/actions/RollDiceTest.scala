package model.actions

import engine.core.EventSink
import engine.events.DiceRollEvent
import engine.events.root.GameEvent
import model.MutableMatchState
import model.entities.Dice
import model.entities.Dice.Dice
import org.scalatest.flatspec.AnyFlatSpec
import mock.MatchMock._

class RollDiceTest extends AnyFlatSpec {

  behavior of "RollDiceTest"

  it should "fire proper event in execute" in {
    RollDice(new Dice[Int] {
      override def name: String = "Mock dice"

      override def roll: Int = 5
    }).execute((event: GameEvent) => assert(event.isInstanceOf[DiceRollEvent[_]] && event.asInstanceOf[DiceRollEvent[_]].result == 5), default.currentState)
  }

}
