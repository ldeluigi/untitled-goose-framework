package model.rules.operations

import engine.events.EventSink
import model.entities.board.Position
import model.{GameEvent, MatchState, Player}

class StepForwardOperation(player: Player) extends Operation {
  override def execute(state: MatchState, eventSink: EventSink[GameEvent]): MatchState =
    // TODO fire events
    state.updatePlayerPieces(map => map + (player -> map(player).updatePosition {
      _.flatMap(pos => state.matchBoard // If position is none it remains none
        .next(pos.tile)
        .map(Position(_)))
    }))
}
