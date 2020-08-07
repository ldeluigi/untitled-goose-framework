package model.entities

import engine.events.StepMovementEvent
import engine.events.root.GameEvent
import model.MatchState

trait DialogContent {

  def title: String

  def text: String

  def options: Map[String, Option[GameEvent]]
}

object DialogContent {
  def testDialog(state: MatchState): DialogContent = new DialogContent {
    override def title = "Movement bonus!"

    override def text: String = "Make 10 Steps?"

    override def options: Map[String, Option[GameEvent]] = Map("Yes" -> Some(StepMovementEvent(10, state.currentPlayer, state.currentTurn)), "No" -> None)
  }
}

