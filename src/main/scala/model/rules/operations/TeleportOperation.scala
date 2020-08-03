package model.rules.operations

import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.entities.board.Position
import model.{MatchState, Player, Tile}


case class TeleportOperation(player: Player, tile: Tile) extends AbstractStepOperation(player, 0) {

  override def step(state: MatchState, pos: Option[Position]): Option[Position] = Some(Position(tile))

}
