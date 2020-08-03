package model.rules.operations

import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.{MatchState, Player}
import model.entities.board.Position


case class StepBackwardOperation(player: Player) extends Operation {
  override def execute(state: MatchState, eventSink: EventSink[GameEvent]): Unit = {
    // TODO fire events
    state.updatePlayerPiece(player, piece =>
      piece.updatePosition(pos =>
        if (pos isDefined)
          state.matchBoard // If position is none it remains none
            .prev(pos.get.tile)
            .map(Position(_))
        else Some(Position(state.matchBoard.first))
      )
    )
  }
}
