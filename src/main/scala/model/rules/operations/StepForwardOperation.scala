package model.rules.operations

import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.entities.board.Position
import model.{MatchState, Player}


case class StepForwardOperation(player: Player, remainingSteps: Int) extends AbstractStepOperation(player, remainingSteps) {

  override def step(state: MatchState, pos: Option[Position]): Option[Position] = {
    if (pos isDefined)
      state.matchBoard // If position is none it remains none
        .next(pos.get.tile)
        .map(Position(_))
    else Some(Position(state.matchBoard.first))
  }

}

