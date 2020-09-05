package dsl.nodes

import dsl.board.nodes.{FirstTileSelectorNode, TileIdentifiersCollection}
import dsl.rules.actions.nodes.ActionRuleSetNode
import model.PlayerOrderingType.PlayerOrderingType
import model.entities.runtime.{Position, Tile}
import model.rules.actionrules.ActionRule
import model.rules.behaviours.BehaviourRule
import model.rules.cleanup.CleanupRule

trait RuleSetNode extends RuleBookNode {

  def playerRange: Range

  def setPlayerRange(range: Range): Unit

  def selectFirstTileByName(selector: String): Unit

  def selectFirstTileByNumber(selector: Int): Unit

  def getFirstTileSelector: Set[Tile] => Position

  def setPlayerOrderingType(order: PlayerOrderingType): Unit

  def playerOrderingType: PlayerOrderingType

  def cleanupRules: Seq[CleanupRule]

  def actionRules: Set[ActionRule]

  def behaviourRules: Seq[BehaviourRule]

  def actionRuleSetNode: ActionRuleSetNode


}

object RuleSetNode {

  class RuleSetNodeImpl(identifiers: TileIdentifiersCollection) extends RuleSetNode {

    private val playerRangeNode: SingleValueNode[Range] = new SingleValueNode("Number of players")

    val actionRuleSetNode: ActionRuleSetNode = ActionRuleSetNode()

    private val firstTileSelectorNode: FirstTileSelectorNode = new FirstTileSelectorNode(identifiers)

    private val playerOrderingTypeNode: SingleValueNode[PlayerOrderingType] = new SingleValueNode[PlayerOrderingType]("Order of players")

    override def setPlayerRange(range: Range): Unit = playerRangeNode.value = range

    override def selectFirstTileByName(selector: String): Unit = firstTileSelectorNode.setValue(selector)

    override def selectFirstTileByNumber(selector: Int): Unit = firstTileSelectorNode.setValue(selector)

    override def playerRange: Range = playerRangeNode.value

    override def getFirstTileSelector: Set[Tile] => Position = tiles => firstTileSelectorNode.getFirstTile(tiles).map(Position(_)).get

    override def setPlayerOrderingType(order: PlayerOrderingType): Unit = playerOrderingTypeNode.value = order

    override def playerOrderingType: PlayerOrderingType = playerOrderingTypeNode.value

    val cleanupRules: Seq[CleanupRule] = Seq()

    val behaviourRules: Seq[BehaviourRule] = Seq()

    val actionRules: Set[ActionRule] = Set()


    override def check: Seq[String] =
      playerRangeNode.check ++
        firstTileSelectorNode.check ++
        playerOrderingTypeNode.check ++
        actionRuleSetNode.check
  }

  def apply(identifiers: TileIdentifiersCollection): RuleSetNode = new RuleSetNodeImpl(identifiers)
}
