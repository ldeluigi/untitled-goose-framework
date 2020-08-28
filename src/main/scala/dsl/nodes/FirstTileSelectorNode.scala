package dsl.nodes

import model.Tile

class FirstTileSelectorNode(identifiers: DefinedTileIdentifiers) extends RuleBookNode {

  private var name: Option[String] = None
  private var num: Option[Int] = None

  def isDefined: Boolean = (name.isDefined || num.isDefined) && !(name.isDefined && num.isDefined)

  def setValue(value: String): Unit = name = Some(value)

  def setValue(value: Int): Unit = num = Some(value)

  def getFirstTile(tiles: Set[Tile]): Option[Tile] = {
    if (name.isDefined) {
      tiles.find(t => t.definition.name.isDefined && t.definition.name.get == name.get)
    } else if (num.isDefined) {
      tiles.find(t => t.definition.name.isDefined && t.definition.name.get == name.get)
    } else None
  }

  override def check: Seq[String] = {
    var messageSeq = if (name.isDefined || num.isDefined) Seq() else Seq("Missing: First tile selector")
    if (name.isDefined && num.isDefined) {
      messageSeq :+= "Conflicting first tile selector choose only one"
    }
    if (name.isDefined && !identifiers.containsName(name.get)) {
      messageSeq :+= ("\"" + name.get + "\" selected as first tile is not defined in this board")
    }
    if (num.isDefined && !identifiers.containsNumber(num.get)) {
      messageSeq :+= ("Tile " + num.get + " selected as first tile is not defined in this board")
    }
    messageSeq
  }
}
