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

  private var errorMessages: Seq[String] = Seq()


  override def check: Seq[String] = {
    //TODO add double name for different tiles check? redefinition of tiles names?
    errorMessages ++= (if (isCompletable) Seq() else Seq("BoardDefinition definition not complete: "))

    if (!nameDefined) {
      errorMessages :+= "\tName not defined"
    }
    if (!dispositionDefined) {
      errorMessages :+= "\tDisposition not defined"
    }
    if (!numberDefined) {
      errorMessages :+= "\tNumber of tiles not defined"
    }
    if (!firstDefined) {
      errorMessages :+= "\tFirst tile not defined"
    }
    errorMessages ++ definedNumbers.filter(!tileRange.contains(_)).map("Tile " + _ + " define properties but is not valid in this definition")
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
    builder.withDisposition(disposition)
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
    builder.withGroupedTiles(group, number: _*)
    definedNumbers ++= number
    definedGroups += group
    this
  }

  override def withFirstTile(tile: TileIdentifier): BoardBuilder = {
    builder.withFirstTile(tile)
    firstDefined = true
    this
  }

  override def withGroupedTiles(group: String, newGroup: String): BoardBuilder = {
    builder.withGroupedTiles(group, newGroup)
    definedGroups += newGroup
    this
  }

  override def complete(): BoardDefinition = {
    builder.complete()
  }

  override def isCompletable: Boolean =
    builder.isCompletable

}