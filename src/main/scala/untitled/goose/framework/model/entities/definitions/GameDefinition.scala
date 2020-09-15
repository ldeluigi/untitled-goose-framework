package untitled.goose.framework.model.entities.definitions

import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.behaviours.BehaviourRule
import untitled.goose.framework.model.rules.cleanup.CleanupRule
import untitled.goose.framework.model.rules.ruleset.PlayerOrderingType.PlayerOrderingType

/** The definition of a game. */
trait GameDefinition {

  /** The board definition. */
  def board: BoardDefinition

  /** Minimum and maximum number of players. */
  def playersRange: Range

  /** Type of player order. */
  def playerOrderingType: PlayerOrderingType

  /** The set of action rules. */
  def actionRules: Set[ActionRule]

  /** The list of behaviour rules, ordered by priority. */
  def behaviourRules: Seq[BehaviourRule]

  /** The list of cleanup rules, ordered by execution order. */
  def cleanupRules: Seq[CleanupRule]
}

object GameDefinition {

  /** Utility case class for a GameDefinition. */
  case class GameDefinitionImpl(board: BoardDefinition,
                                playerOrderingType: PlayerOrderingType,
                                playersRange: Range,
                                actionRules: Set[ActionRule],
                                behaviourRules: Seq[BehaviourRule],
                                cleanupRules: Seq[CleanupRule]) extends GameDefinition

}
