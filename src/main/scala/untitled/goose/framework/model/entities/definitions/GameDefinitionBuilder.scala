package untitled.goose.framework.model.entities.definitions

import untitled.goose.framework.model.PlayerOrderingType.PlayerOrderingType
import untitled.goose.framework.model.entities.definitions.GameDefinition.GameDefinitionImpl
import untitled.goose.framework.model.entities.runtime.{Position, Tile}
import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.behaviours.BehaviourRule
import untitled.goose.framework.model.rules.cleanup.CleanupRule

trait GameDefinitionBuilder {
  def board(board: BoardDefinition): GameDefinitionBuilder

  def startPositionStrategy(strategy: Set[Tile] => Position): GameDefinitionBuilder

  def playerOrderingType(ordering: PlayerOrderingType): GameDefinitionBuilder

  def playersRange(range: Range): GameDefinitionBuilder

  def actionRules(rules: Set[ActionRule]): GameDefinitionBuilder

  def behaviourRules(orderedRules: Seq[BehaviourRule]): GameDefinitionBuilder

  def cleanupRules(orderedRules: Seq[CleanupRule]): GameDefinitionBuilder

  def build: GameDefinition
}

object GameDefinitionBuilder {

  private class GameDefinitionBuilderImpl() extends GameDefinitionBuilder {
    private var _board: Option[BoardDefinition] = None

    override def board(board: BoardDefinition): GameDefinitionBuilder = {
      _board = Some(board)
      this
    }

    private var _startPositionStrategy: Option[Set[Tile] => Position] = None

    override def startPositionStrategy(strategy: Set[Tile] => Position): GameDefinitionBuilder = {
      _startPositionStrategy = Some(strategy)
      this
    }

    private var _orderingType: Option[PlayerOrderingType] = None

    override def playerOrderingType(ordering: PlayerOrderingType): GameDefinitionBuilder = {
      _orderingType = Some(ordering)
      this
    }

    private var _playersRange: Option[Range] = None

    override def playersRange(range: Range): GameDefinitionBuilder = {
      _playersRange = Some(range)
      this
    }

    private var _actionRules: Option[Set[ActionRule]] = None

    override def actionRules(rules: Set[ActionRule]): GameDefinitionBuilder = {
      _actionRules = Some(rules)
      this
    }

    private var _behaviorRules: Option[Seq[BehaviourRule]] = None

    override def behaviourRules(orderedRules: Seq[BehaviourRule]): GameDefinitionBuilder = {
      _behaviorRules = Some(orderedRules)
      this
    }

    private var _cleanupRules: Option[Seq[CleanupRule]] = None

    override def cleanupRules(orderedRules: Seq[CleanupRule]): GameDefinitionBuilder = {
      _cleanupRules = Some(orderedRules)
      this
    }

    override def build: GameDefinition =
      GameDefinitionImpl(_board.get,
        _startPositionStrategy.get,
        _orderingType.get,
        _playersRange.get,
        _actionRules.get,
        _behaviorRules.get,
        _cleanupRules.get)
  }

  def apply(): GameDefinitionBuilder = new GameDefinitionBuilderImpl()
}