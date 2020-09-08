package untitled.goose.framework.dsl.nodes

import untitled.goose.framework.dsl.board.nodes.TileIdentifiersCollection
import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleSetNode
import untitled.goose.framework.model.rules.ruleset.PlayerOrderingType.PlayerOrderingType
import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.behaviours.BehaviourRule
import untitled.goose.framework.model.rules.cleanup.CleanupRule

trait RuleSetNode extends RuleBookNode {

  def playerRange: Range

  def setPlayerRange(range: Range): Unit

  def setPlayerOrderingType(order: PlayerOrderingType): Unit

  def playerOrderingType: PlayerOrderingType

  def cleanupRules: Seq[CleanupRule]

  def actionRules: Set[ActionRule]

  def behaviourRules: Seq[BehaviourRule]

  def actionRuleSetNode: ActionRuleSetNode


}

object RuleSetNode {

  class RuleSetNodeImpl extends RuleSetNode {

    private val playerRangeNode: SingleValueNode[Range] = new SingleValueNode("Number of players")

    val actionRuleSetNode: ActionRuleSetNode = ActionRuleSetNode()

    private val playerOrderingTypeNode: SingleValueNode[PlayerOrderingType] = new SingleValueNode[PlayerOrderingType]("Order of players")

    override def setPlayerRange(range: Range): Unit = playerRangeNode.value = range

    override def playerRange: Range = playerRangeNode.value

    override def setPlayerOrderingType(order: PlayerOrderingType): Unit = playerOrderingTypeNode.value = order

    override def playerOrderingType: PlayerOrderingType = playerOrderingTypeNode.value

    val cleanupRules: Seq[CleanupRule] = Seq()

    val behaviourRules: Seq[BehaviourRule] = Seq()

    val actionRules: Set[ActionRule] = Set()


    override def check: Seq[String] =
      playerRangeNode.check ++
        playerOrderingTypeNode.check ++
        actionRuleSetNode.check
  }

  def apply(identifiers: TileIdentifiersCollection): RuleSetNode = new RuleSetNodeImpl
}
