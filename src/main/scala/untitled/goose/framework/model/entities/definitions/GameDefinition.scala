package untitled.goose.framework.model.entities.definitions

import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.behaviours.BehaviourRule
import untitled.goose.framework.model.rules.cleanup.CleanupRule
import untitled.goose.framework.model.rules.ruleset.PlayerOrdering

/** The definition of a game. */
trait GameDefinition {

  /** The board definition. */
  def board: BoardDefinition

  /** Minimum and maximum number of players. */
  def playersRange: Range

  /** Player order strategy. */
  def playerOrdering: PlayerOrdering

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
                                playerOrdering: PlayerOrdering,
                                playersRange: Range,
                                actionRules: Set[ActionRule],
                                behaviourRules: Seq[BehaviourRule],
                                cleanupRules: Seq[CleanupRule]) extends GameDefinition

}
