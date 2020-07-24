package model.rules.operations

import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.entities.board.Position
import model.{MatchState, Player}

class StepForwardOperation(player: Player) extends Operation {
  override def execute(state: MatchState, eventSink: EventSink[GameEvent]): Unit = {
    // TODO fire events
    state.updatePlayerPiece(player, piece =>
      piece.updatePosition(pos =>
        if (pos isDefined)
          state.matchBoard // If position is none it remains none
          .next(pos.get.tile)
          .map(Position(_))
        else Some(Position(state.matchBoard.first))
      )
    )
  }
}

object StepForwardOperation {
  def apply(player: Player): StepForwardOperation = new StepForwardOperation(player)
}
