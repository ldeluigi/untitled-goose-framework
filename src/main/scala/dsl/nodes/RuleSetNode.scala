package dsl.nodes

import model.Tile
import model.entities.board.Position
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}

trait RuleSetNode extends RuleBookNode {
  def setPlayerRange(range: Range): Unit

  def selectFirstTileByName(selector: String): Unit

  def selectFirstTileByNumber(selector: Int): Unit

  def getRuleSet: RuleSet
}

object RuleSetNode {

  class RuleSetNodeImpl(identifiers: DefinedTileIdentifiers) extends RuleSetNode {

    private val playerRange: SingleValueNode[Range] = new SingleValueNode("Number of players")

    private val firstTileSelectorNode: FirstTileSelectorNode = new FirstTileSelectorNode(identifiers)

    override def setPlayerRange(range: Range): Unit = playerRange.value = range

    override def selectFirstTileByName(selector: String): Unit = firstTileSelectorNode.setValue(selector)

    override def selectFirstTileByNumber(selector: Int): Unit = firstTileSelectorNode.setValue(selector)

    override def check: Seq[String] = {
      playerRange.check ++
        firstTileSelectorNode.check
    }

    override def getRuleSet: RuleSet = {
      val firstTile: Set[Tile] => Position = tiles => Position(firstTileSelectorNode.getFirstTile(tiles).get)

      //TODO complete generation with correct fields
      PriorityRuleSet(
        firstTile,
        PlayerOrdering.orderedRandom,
        playerRange.value,
        Set(),
        Seq())
    }

  }

  def apply(identifiers: DefinedTileIdentifiers): RuleSetNode = new RuleSetNodeImpl(identifiers)
}
