package dsl.nodes

import model.entities.definitions.{Board, BoardBuilder, Disposition}

case class BoardBuilderNode() extends RuleBookNode with BoardBuilder with DefinedTileIdentifiers {

  private val builder: BoardBuilder = BoardBuilder()
  private var nameDefined = false
  private var numberDefined = false
  private var dispositionDefined = false


  private var definedTileNames: Set[String] = Set()
  private var definedGroups: Set[String] = Set()
  private var definedNumbers: Set[Int] = Set()

  private var tileRange: Range = 0 to 0

  override def containsName(name: String): Boolean = definedTileNames.contains(name)

  override def containsGroup(group: String): Boolean = definedGroups.contains(group)

  override def containsNumber(num: Int): Boolean = tileRange.contains(num)

  private var errorMessages: Seq[String] = Seq()


  override def check: Seq[String] = {
    errorMessages ++= (if (isCompletable) Seq() else Seq("Board definition not complete: "))

    if (!nameDefined) {
      errorMessages :+= "\tName not defined"
    }
    if (!dispositionDefined) {
      errorMessages :+= "\tDisposition not defined"
    }
    if (!numberDefined) {
      errorMessages :+= "\tNumber of tiles not defined"
    }
    errorMessages ++ definedNumbers.filter(!tileRange.contains(_)).map("Tile " + _ + " define properties but is not valid in this board")
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

  override def withGroupedTiles(originGroup: String, newGroup: String): BoardBuilder = {
    builder.withGroupedTiles(originGroup, newGroup)
    definedGroups += newGroup
    this
  }

  override def complete(): Board = {
    builder.complete()
  }

  override def isCompletable: Boolean =
    builder.isCompletable
}
