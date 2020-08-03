package model.rules.operations

import model.{MatchState, Player}
import model.entities.board.Position


case class StepBackwardOperation(player: Player, remainingSteps: Int) extends AbstractStepOperation(player, remainingSteps) {

  override def step(state: MatchState, pos: Option[Position]): Option[Position] = {
    if (pos isDefined)
      state.matchBoard // If position is none it remains none
        .prev(pos.get.tile)
        .map(Position(_))
    else Some(Position(state.matchBoard.first))
  }

}
