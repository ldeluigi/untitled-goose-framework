package untitled.goose.framework.model.entities.definitions

import untitled.goose.framework.model.rules.ruleset.PlayerOrderingType.PlayerOrderingType
import untitled.goose.framework.model.entities.definitions.GameDefinition.GameDefinitionImpl
import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.behaviours.BehaviourRule
import untitled.goose.framework.model.rules.cleanup.CleanupRule

/** Builder pattern implemented for a [[GameDefinition]]. */
trait GameDefinitionBuilder {

  /** Sets the board definition. */
  def board(board: BoardDefinition): GameDefinitionBuilder

  /** Sets the player ordering type. */
  def playerOrderingType(ordering: PlayerOrderingType): GameDefinitionBuilder

  /** Sets the minimum and maximum number of player allowed. */
  def playersRange(range: Range): GameDefinitionBuilder

  /** Sets the action rules set. */
  def actionRules(rules: Set[ActionRule]): GameDefinitionBuilder

  /** Sets the behaviour rules list, sorted by priority. */
  def behaviourRules(orderedRules: Seq[BehaviourRule]): GameDefinitionBuilder

  /** Sets the cleanup rules list, sorted by execution order. */
  def cleanupRules(orderedRules: Seq[CleanupRule]): GameDefinitionBuilder

  /** Completes the GameDefinition build process. */
  def build: GameDefinition
}

object GameDefinitionBuilder {

  private class GameDefinitionBuilderImpl() extends GameDefinitionBuilder {
    private var _board: Option[BoardDefinition] = None

    override def board(board: BoardDefinition): GameDefinitionBuilder = {
      _board = Some(board)
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
        _orderingType.get,
        _playersRange.get,
        _actionRules.get,
        _behaviorRules.get,
        _cleanupRules.get)
  }

  /** Factory method for an empty builder. */
  def apply(): GameDefinitionBuilder = new GameDefinitionBuilderImpl()
}