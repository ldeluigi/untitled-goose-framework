package model.rules.operations

import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.entities.board.Position
import model.{MatchState, Player}

class StepForwardOperation(player: Player) extends Operation {
  override def execute(state: MatchState, eventSink: EventSink[GameEvent]): MatchState = ???
    // TODO fire events and update status
//    state.updatePlayerPieces(map => map + (player -> map(player).updatePosition {
//      _.flatMap(pos => state.matchBoard // If position is none it remains none
//        .next(pos.tile)
//        .map(Position(_)))
//    }))
}
