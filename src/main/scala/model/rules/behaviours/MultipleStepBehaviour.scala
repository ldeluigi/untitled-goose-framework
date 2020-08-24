package model.rules.behaviours

import engine.events
import engine.events._
import model.Player
import model.entities.board.{Piece, Position}
import model.game.GameState
import model.game.GameStateExtensions.{MatchStateExtensions, PimpedHistory}
import model.rules.BehaviourRule
import model.rules.operations.Operation

/** Creates a multiple-step related behaviour rule. */
case class MultipleStepBehaviour() extends BehaviourRule {

  override def name: Option[String] = Some("Multiple StepRule")

  override def applyRule(state: GameState): Seq[Operation] = {
    state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .filter(_.isInstanceOf[StepMovementEvent])
      .map(_.asInstanceOf[StepMovementEvent])
      .consumeAll()
      .flatMap(e => generateStep(state, e.movement, e.source, e.movement >= 0))
  }

  /** Generates a step in the game.
   *
   * @param state   the current game state
   * @param step    the number of steps to process
   * @param player  the player that steps forward or backwards
   * @param forward the direction of the step
   * @return the resulting sequence of operation
   */
  private def generateStep(state: GameState, step: Int, player: Player, forward: Boolean): Seq[Operation] = {
    (1 to step).toList.flatMap(i => {
      stepOperation(state, player, forward, step - i)
    })
  }

  /** Actual step action.
   *
   * @param state          the current game state
   * @param player         the player that steps forward or backwards
   * @param forward        the direction of the step
   * @param remainingSteps the remaining number of steps to process
   * @return the resulting sequence of operation
   */
  private def stepOperation(state: GameState, player: Player, forward: Boolean, remainingSteps: Int): Seq[Operation] = {

    val tileExited = Operation.trigger(s => {
      val tile = s.playerPieces(player).position.map(_.tile)
      if (tile.isDefined) {
        Some(TileExitedEvent(player, tile.get, state.currentTurn))
      } else {
        None
      }
    })

    val triggerPassedPlayers: Seq[Operation] = checkAndTriggerPassedPlayers(state, player)

    val step = Operation.execute(state => {
      val inverted = player.history.filterTurn(state.currentTurn).find(_.isInstanceOf[InvertMovementEvent])
      state.updatePlayerPiece(player, piece => {
        Piece(piece, piece.position match {
          case Some(pos) => if (forward) {
            if (inverted.isEmpty) {
              state.gameBoard
                .next(pos.tile)
                .map(Position(_))
            } else {
              state.gameBoard
                .prev(pos.tile)
                .map(Position(_))
            }
          } else {
            if (inverted.isDefined) {
              state.gameBoard
                .next(pos.tile)
                .map(Position(_))
            } else {
              state.gameBoard
                .prev(pos.tile)
                .map(Position(_))
            }
          }
          case None => Some(Position(state.gameBoard.first))
        })
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

    val opSeq = tileExited +: triggerPassedPlayers :+ step :+ tileEntered

    if (remainingSteps == 0) {
      opSeq :+ tileStopped
    } else {
      opSeq
    }
  }

  /** Triggers the corresponding operations related to a certain tile the player has stepped onto.
   *
   * @param state  the current game state
   * @param player the player to check
   * @return the resulting sequence of operation
   */
  private def checkAndTriggerPassedPlayers(state: GameState, player: Player): Seq[Operation] = {
    for (other <- state.players.toSeq if !other.equals(player))
      yield Operation.trigger(s => {
        val tile = s.playerPieces(player).position.map(_.tile)
        if (tile.isDefined) {
          if (state.playerLastTurn(other).exists(l => state.playerStopsTurns(tile.get, other).contains(l))) {
            Some(events.PlayerPassedEvent(other, player, tile.get, s.currentTurn))
          } else None
        } else None
      })
  }


}
