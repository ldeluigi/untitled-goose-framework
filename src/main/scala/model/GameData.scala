package model

import model.PlayerOrderingType._
import model.entities.board.{Board, Piece, Position}
import model.game.Game
import model.rules.behaviours.BehaviourRule
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}
import model.rules.{ActionRule, CleanupRule}

trait GameData {
  def board: Board

  def playersRange: Range

  def createGame(players: Seq[Player], playerPieces: Map[Player, Piece]): Game
}

object GameData {

  private class GameDataImpl(val board: Board,
                             firstPosition: Set[Tile] => Position,
                             playerOrderingType: PlayerOrderingType,
                             val playersRange: Range,
                             actionRules: Set[ActionRule],
                             behaviourRules: Seq[BehaviourRule],
                             cleanupRules: Seq[CleanupRule]) extends GameData {

    override def createGame(players: Seq[Player], playerPieces: Map[Player, Piece]): Game = {
      val playerOrdering: PlayerOrdering = playerOrderingType match {
        case FullRandom => PlayerOrdering.fullRandom
        case RandomOrder => PlayerOrdering.randomOrder
        case GivenOrder => PlayerOrdering.givenOrder(players)
      }
      val ruleSet: RuleSet = PriorityRuleSet(
        firstPosition,
        playerOrdering,
        playersRange,
        actionRules,
        behaviourRules,
        cleanupRules
      )
      Game(board, playerPieces, ruleSet)
    }
  }

  def apply(board: Board,
            firstPosition: Set[Tile] => Position,
            playerOrderingType: PlayerOrderingType,
            admissiblePlayers: Range,
            actionRules: Set[ActionRule],
            behaviourRules: Seq[BehaviourRule],
            cleanupRules: Seq[CleanupRule]
           ): GameData = new GameDataImpl(board, firstPosition, playerOrderingType, admissiblePlayers, actionRules, behaviourRules, cleanupRules)
}