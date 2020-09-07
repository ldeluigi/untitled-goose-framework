package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.PlayerOrderingType.{FullRandom, PlayerOrderingType, RandomOrder, UserDefinedOrder}
import untitled.goose.framework.model.entities.definitions.BoardDefinition
import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.behaviours.BehaviourRule
import untitled.goose.framework.model.rules.cleanup.CleanupRule
import untitled.goose.framework.model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}

trait GameTemplateBuilder {
  def board(board: BoardDefinition): GameTemplateBuilder

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
    private var _board: Option[BoardDefinition] = None

    override def board(board: BoardDefinition): GameTemplateBuilder = {
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

  private class GameTemplateImpl(board: BoardDefinition,
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