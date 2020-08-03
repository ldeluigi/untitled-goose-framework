package model.rules.operations

import engine.events.{StopOnTileEvent, TileEnteredEvent, TileExitedEvent}
import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.{MatchState, Player}
import model.entities.board.Position

abstract class AbstractStepOperation(player: Player, remainingSteps: Int) extends Operation {

  override def execute(state: MatchState, eventSink: EventSink[GameEvent]): Unit = {

    val oldPosition = state.playerPieces(state.currentPlayer).position
    val newPosition = step(state, oldPosition)

    if (oldPosition.isDefined) {
      eventSink.accept(TileExitedEvent(oldPosition.get.tile, state.currentTurn))
    }

    if (newPosition.isDefined) {
      eventSink.accept(TileEnteredEvent(oldPosition.get.tile, state.currentTurn))
    }

    if (remainingSteps == 0) {
      eventSink.accept(StopOnTileEvent(oldPosition.get.tile, state.currentTurn))
    }

    state.updatePlayerPiece(player, piece =>
      piece.updatePosition(_ => newPosition)
    )
  }

  def step(state: MatchState, pos: Option[Position]): Option[Position]


}
