package model.entities.runtime

import model.PlayerOrderingType.{FullRandom, PlayerOrderingType, RandomOrder, UserDefinedOrder}
import model.entities.definitions.Board
import model.rules.actionrules.ActionRule
import model.rules.behaviours.BehaviourRule
import model.rules.cleanup.CleanupRule
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}

trait GameTemplateBuilder {
  def board(board: Board): GameTemplateBuilder

  def startPositionStrategy(strategy: Set[Tile] => Position): GameTemplateBuilder

  def playerOrderingType(ordering: PlayerOrderingType): GameTemplateBuilder

  def playersRange(range: Range): GameTemplateBuilder

  def actionRules(rules: Set[ActionRule]): GameTemplateBuilder

  def behaviourRules(orderedRules: Seq[BehaviourRule]): GameTemplateBuilder

  def cleanupRules(orderedRules: Seq[CleanupRule]): GameTemplateBuilder

  def build: GameTemplate
}

object GameTemplateBuilder {

  private class GameTemplateBuilderImpl() extends GameTemplateBuilder {
    private var _board: Option[Board] = None

    override def board(board: Board): GameTemplateBuilder = {
      _board = Some(board)
      this
    }

    private var _startPositionStrategy: Option[Set[Tile] => Position] = None

    override def startPositionStrategy(strategy: Set[Tile] => Position): GameTemplateBuilder = {
      _startPositionStrategy = Some(strategy)
      this
    }

    private var _orderingType: Option[PlayerOrderingType] = None

    override def playerOrderingType(ordering: PlayerOrderingType): GameTemplateBuilder = {
      _orderingType = Some(ordering)
      this
    }

    private var _playersRange: Option[Range] = None

    override def playersRange(range: Range): GameTemplateBuilder = {
      _playersRange = Some(range)
      this
    }

    private var _actionRules: Option[Set[ActionRule]] = None

    override def actionRules(rules: Set[ActionRule]): GameTemplateBuilder = {
      _actionRules = Some(rules)
      this
    }

    private var _behaviorRules: Option[Seq[BehaviourRule]] = None

    override def behaviourRules(orderedRules: Seq[BehaviourRule]): GameTemplateBuilder = {
      _behaviorRules = Some(orderedRules)
      this
    }

    private var _cleanupRules: Option[Seq[CleanupRule]] = None

    override def cleanupRules(orderedRules: Seq[CleanupRule]): GameTemplateBuilder = {
      _cleanupRules = Some(orderedRules)
      this
    }

    override def build: GameTemplate =
      new GameTemplateImpl(_board.get,
        _startPositionStrategy.get,
        _orderingType.get,
        _playersRange.get,
        _actionRules.get,
        _behaviorRules.get,
        _cleanupRules.get)
  }

  private class GameTemplateImpl(board: Board,
                                 startPositionStrategy: Set[Tile] => Position,
                                 playerOrderingType: PlayerOrderingType,
                                 val playersRange: Range,
                                 actionRules: Set[ActionRule],
                                 behaviourRules: Seq[BehaviourRule],
                                 cleanupRules: Seq[CleanupRule]) extends GameTemplate {

    override def createGame(players: Seq[Player], playerPieces: Map[Player, Piece]): Game = {
      val playerOrdering: PlayerOrdering = playerOrderingType match {
        case FullRandom => PlayerOrdering.fullRandom
        case RandomOrder => PlayerOrdering.randomOrder(7)
        case UserDefinedOrder => PlayerOrdering.givenOrder(players)
      }
      val ruleSet: RuleSet = PriorityRuleSet(
        startPositionStrategy,
        playerOrdering,
        playersRange,
        actionRules,
        behaviourRules,
        cleanupRules
      )
      Game(board, playerPieces, ruleSet)
    }
  }

  def apply(): GameTemplateBuilder = new GameTemplateBuilderImpl()
}