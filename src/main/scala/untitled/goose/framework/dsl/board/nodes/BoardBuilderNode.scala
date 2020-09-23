package untitled.goose.framework.dsl.board.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.entities.definitions.{BoardBuilder, BoardDefinition, Disposition, TileIdentifier}

case class BoardBuilderNode() extends RuleBookNode with BoardBuilder with TileIdentifiersCollection {

  private val builder: BoardBuilder = BoardBuilder()
  private var nameDefined = false
  private var numberDefined = false
  private var dispositionDefined = false
  private var firstDefined = false


  private var definedTileNames: Set[String] = Set()
  private var definedGroups: Set[String] = Set()
  private var definedNumbers: Set[Int] = Set()

  private var tileRange: Range = 0 to 0

  override def containsName(name: String): Boolean = definedTileNames.contains(name)

  override def containsGroup(group: String): Boolean = definedGroups.contains(group)

  override def containsNumber(num: Int): Boolean = tileRange.contains(num)

  override def check: Seq[String] = {
    {
      if (isCompletable)
        Seq()
      else
        Seq(
          true -> "BoardDefinition definition not complete: ",
          !nameDefined -> "\tName not defined",
          !dispositionDefined -> "\tDisposition not defined",
          !numberDefined -> "\tNumber of tiles not defined",
          !firstDefined -> "\tFirst tile not defined"
        ).flatMap(t => if (t._1) Seq(t._2) else Seq())
    } ++
      definedNumbers.filter(!tileRange.contains(_))
        .map("Tile " + _ + " define properties but is not valid in this definition")
  }

  override def withName(name: String): BoardBuilder = {
    builder withName name
    nameDefined = true
    this
  }

  override def withNumberedTiles(total: Int, from: Int): BoardBuilder = {
    builder withNumberedTiles(total, from)
    tileRange = from to total
    numberDefined = true
    this
  }

  override def withDisposition(disposition: Int => Disposition): BoardBuilder = {
    builder withDisposition disposition
    dispositionDefined = true
    this
  }

  override def withNamedTile(number: Int, name: String): BoardBuilder = {
    builder withNamedTile(number, name)
    definedNumbers += number
    definedTileNames += name
    this
  }

  override def withGroupedTiles(group: String, number: Int*): BoardBuilder = {
    builder withGroupedTiles(group, number: _*)
    definedNumbers ++= number
    definedGroups += group
    this
  }

  override def withFirstTile(tile: TileIdentifier): BoardBuilder = {
    builder withFirstTile tile
    firstDefined = true
    this
  }

  override def withGroupedTiles(group: String, newGroup: String): BoardBuilder = {
    builder withGroupedTiles(group, newGroup)
    definedGroups += newGroup
    this
  }

  override def complete(): BoardDefinition = {
    builder complete()
  }

  override def isCompletable: Boolean =
    builder isCompletable

}
