package dsl.nodes

import model.entities.board.{Board, BoardBuilder, BoardBuilderImpl, Disposition}

case class BoardBuilderNode() extends RuleBookNode with BoardBuilder {

  private val builder: BoardBuilderImpl = BoardBuilderImpl()
  private var nameDefined = false
  private var numberDefined = false
  private var dispositionDefined = false


  private var definedTileNames: Set[String] = Set()
  private var definedGroups: Set[String] = Set()

  private var tileRange: Range = 0 to 0


  def nameIsDefined(name: String): Boolean = definedTileNames.contains(name)

  def groupIsDefined(group: String): Boolean = definedGroups.contains(group)

  def definedTiles: Range = tileRange


  override def check: Seq[String] = {
    var errorMessages = if (isCompletable) Seq() else Seq("Board definition not complete: ")

    if (!nameDefined) {
      errorMessages :+= "\tName not defined"
    }
    if (!dispositionDefined) {
      errorMessages :+= "\tDisposition not defined"
    }
    if (!numberDefined) {
      errorMessages :+= "\tNumber of tiles not defined"
    }
    errorMessages
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
    definedTileNames += name
    this
  }

  override def withGroupedTiles(group: String, number: Int*): BoardBuilder = {
    builder.withGroupedTiles(group, number: _*)
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
