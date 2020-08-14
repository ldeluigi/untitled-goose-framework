package model.rules.behaviours

import engine.events
import engine.events._
import model.entities.board.{Piece, Position}
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

  private def generateStep(state: MatchState, step: Int, player: Player, forward: Boolean): Seq[Operation] = {
    (1 to step).toList.flatMap(i => {
      stepOperation(state, player, forward, step - i)
    })
  }

  private def stepOperation(state: MatchState, player: Player, forward: Boolean, remainingSteps: Int): Seq[Operation] = {

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
      state.updatePlayerPiece(player, piece => {
        Piece(piece, piece.position match {
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

  //TODO ADDED THIS TO BE REVIEWED
  private def checkAndTriggerPassedPlayers(state: MatchState, player: Player): Seq[Operation] = {
    for (other <- state.playerPieces.keySet.toSeq if !other.equals(player))
      yield Operation.trigger(s => {
        val tile = s.playerPieces(player).position.map(_.tile)
        if (tile.isDefined) {
          //TODO should this be added as extension method to the tile?
          val turnsOtherStopped = tile.get.history
            .filter(_.isInstanceOf[StopOnTileEvent])
            .map(_.asInstanceOf[StopOnTileEvent])
            .filter(_.player.equals(other))
            .map(_.turn)
          //TODO should this be added as extension method to the state?
          val lastTurnPlayed = other.history.filter(_.isInstanceOf[TurnEndedEvent])
            .map(_.turn).reduceOption(_ max _).getOrElse(-1)

          if (turnsOtherStopped.contains(lastTurnPlayed)) {
            Some(events.PlayerPassedEvent(player, other, tile.get, s.currentTurn))
          } else None
        } else None
      })
  }


}
