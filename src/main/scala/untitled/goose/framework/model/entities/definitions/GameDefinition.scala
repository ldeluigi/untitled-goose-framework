package untitled.goose.framework.model.entities.definitions

import untitled.goose.framework.model.PlayerOrderingType.PlayerOrderingType
import untitled.goose.framework.model.entities.runtime.{Position, Tile}
import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.behaviours.BehaviourRule
import untitled.goose.framework.model.rules.cleanup.CleanupRule

trait GameDefinition {
  def startPositionStrategy: Set[Tile] => Position

  def board: BoardDefinition

  def playersRange: Range

  def playerOrderingType: PlayerOrderingType

  def actionRules: Set[ActionRule]

  def behaviourRules: Seq[BehaviourRule]

  def cleanupRules: Seq[CleanupRule]
}

object GameDefinition {

  case class GameDefinitionImpl(board: BoardDefinition,
                                startPositionStrategy: Set[Tile] => Position,
                                playerOrderingType: PlayerOrderingType,
                                playersRange: Range,
                                actionRules: Set[ActionRule],
                                behaviourRules: Seq[BehaviourRule],
                                cleanupRules: Seq[CleanupRule]) extends GameDefinition

}
