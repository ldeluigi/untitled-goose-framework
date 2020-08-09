package model.rules.behaviours

import engine.events.{StepMovementEvent, StopOnTileEvent, TileEnteredEvent, TileExitedEvent}
import model.entities.board.Position
import model.rules.BehaviourRule
import model.rules.operations.Operation
import model.{MatchState, Player}

case class MultipleStepBehaviour() extends BehaviourRule {

  override def name: Option[String] = Some("Multiple StepRule")

  override def applyRule(state: MatchState): Seq[Operation] = {
    state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[StepMovementEvent])
      .map(e => {
        e.consume()
        e.asInstanceOf[StepMovementEvent]
      })
      .flatMap(e => generateStep(state, e.movement, e.source, e.movement >= 0))
  }

  def generateStep(state: MatchState, step: Int, player: Player, forward: Boolean): Seq[Operation] = {
    (1 to step).toList.flatMap(i => {
      stepOperation(state, player, forward, step - i)
    })
  }

  def stepOperation(state: MatchState, player: Player, forward: Boolean, remainingSteps: Int): Seq[Operation] = {

    val tileExited = Operation.trigger(s => {
      val tile = s.playerPieces(player).position.map(_.tile)
      if (tile.isDefined) {
        Some(TileExitedEvent(player, tile.get, state.currentTurn))
      } else {
        None
      }
    })

    val step = Operation.execute(state => {
      state.updatePlayerPiece(player, piece => {
        piece.updatePosition {
          case Some(pos) => if (forward) {
            state.matchBoard
              .next(pos.tile)
              .map(Position(_))
          } else {
            state.matchBoard
              .prev(pos.tile)
              .map(Position(_))
          }
          case None => Some(Position(state.matchBoard.first))
        }
      })
    })

    val tileEntered = Operation.trigger(s => {
      val tile = s.playerPieces(player).position.map(_.tile)
      if (tile.isDefined) {
        Some(TileEnteredEvent(player, tile.get, state.currentTurn))
      } else {
        None
      }
    })

    val tileStopped = Operation.trigger(s => {
      val tile = s.playerPieces(player).position.map(_.tile)
      if (tile.isDefined) {
        Some(StopOnTileEvent(player, tile.get, state.currentTurn))
      } else {
        None
      }
    })

    val opSeq = Seq(tileExited, step, tileEntered)

    if (remainingSteps == 0) {
      opSeq :+ tileStopped
    } else {
      opSeq
    }
  }


}
