package untitled.goose.framework.dsl.rules

import untitled.goose.framework.dsl.board.nodes.TileIdentifiersCollection
import untitled.goose.framework.dsl.nodes.{RuleBookNode, SingleValueNode}
import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleSetNode
import untitled.goose.framework.dsl.rules.behaviours.nodes.BehaviourCollectionNode
import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.behaviours.BehaviourRule
import untitled.goose.framework.model.rules.cleanup.CleanupRule
import untitled.goose.framework.model.entities.definitions.PlayerOrderingType.PlayerOrderingType

trait RuleSetNode extends RuleBookNode {

  def playerRange: Range

  def setPlayerRange(range: Range): Unit

  def setPlayerOrderingType(order: PlayerOrderingType): Unit

  def playerOrderingType: PlayerOrderingType

  def cleanupRules: Seq[CleanupRule]

  def cleanupRules_=(c: Seq[CleanupRule]): Unit

  def actionRules: Set[ActionRule]

  def behaviourRules: Seq[BehaviourRule]

  def actionRuleSetNode: ActionRuleSetNode

  def behaviourCollectionNode: BehaviourCollectionNode

}

object RuleSetNode {

  class RuleSetNodeImpl extends RuleSetNode {

    private val playerRangeNode: SingleValueNode[Range] = new SingleValueNode("Number of players")

    val behaviourCollectionNode: BehaviourCollectionNode = BehaviourCollectionNode()

    val actionRuleSetNode: ActionRuleSetNode = ActionRuleSetNode()

    private val playerOrderingTypeNode: SingleValueNode[PlayerOrderingType] = new SingleValueNode[PlayerOrderingType]("Order of players")

    override def setPlayerRange(range: Range): Unit = playerRangeNode.value = range

    override def playerRange: Range = playerRangeNode.value

    override def setPlayerOrderingType(order: PlayerOrderingType): Unit = playerOrderingTypeNode.value = order

    override def playerOrderingType: PlayerOrderingType = playerOrderingTypeNode.value

    var cleanupRules: Seq[CleanupRule] = Seq()

    def behaviourRules: Seq[BehaviourRule] = behaviourCollectionNode.behaviours

    def actionRules: Set[ActionRule] = actionRuleSetNode.actionRules


    override def check: Seq[String] =
      playerRangeNode.check ++
        playerOrderingTypeNode.check ++
        actionRuleSetNode.check ++
        behaviourCollectionNode.check
  }

  def apply(identifiers: TileIdentifiersCollection): RuleSetNode = new RuleSetNodeImpl
}
