package dsl.nodes

import model.PlayerOrderingType.PlayerOrderingType
import model.Tile
import model.entities.board.Position
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

  def behaviourRules: Seq[BehaviourRule]

  def actionRules: Set[ActionRule]

}

object RuleSetNode {

  class RuleSetNodeImpl(identifiers: DefinedTileIdentifiers) extends RuleSetNode {

    private val playerRangeNode: SingleValueNode[Range] = new SingleValueNode("Number of players")

    private val firstTileSelectorNode: FirstTileSelectorNode = new FirstTileSelectorNode(identifiers)

    private val playerOrderingTypeNode: SingleValueNode[PlayerOrderingType] = new SingleValueNode[PlayerOrderingType]("Order of players")

    override def setPlayerRange(range: Range): Unit = playerRangeNode.value = range

    override def selectFirstTileByName(selector: String): Unit = firstTileSelectorNode.setValue(selector)

    override def selectFirstTileByNumber(selector: Int): Unit = firstTileSelectorNode.setValue(selector)

    override def playerRange: Range = playerRangeNode.value

    override def getFirstTileSelector: Set[Tile] => Position = tiles => firstTileSelectorNode.getFirstTile(tiles).map(Position(_)).get

    override def setPlayerOrderingType(order: PlayerOrderingType): Unit = playerOrderingTypeNode.value = order

    override def playerOrderingType: PlayerOrderingType = playerOrderingTypeNode.value

    val cleanupRules: Seq[CleanupRule] = ???

    val behaviourRules: Seq[BehaviourRule] = ???

    val actionRules: Set[ActionRule] = ???


    override def check: Seq[String] =
      playerRangeNode.check ++
        firstTileSelectorNode.check ++
        playerOrderingTypeNode.check
  }

  def apply(identifiers: DefinedTileIdentifiers): RuleSetNode = new RuleSetNodeImpl(identifiers)
}
