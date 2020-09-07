package untitled.goose.framework.model.entities.definitions

/** Fluent syntax for a definition step-by-step creation. */
trait BoardBuilder {

  /** Defines the name of the game. */
  def withName(name: String): BoardBuilder

  /**
   * Adds numbered tiles to the definition tile set.
   * @param total how many tiles should be added.
   * @param from the number of the first tile.
   * @return this builder.
   */
  def withNumberedTiles(total: Int, from: Int = 1): BoardBuilder

  /**
   * Adds a name to a numbered tile.
   * @param number the tile's number.
   * @param name   the name given to the numbered tile.
   * @return this builder.
   */
  def withNamedTile(number: Int, name: String): BoardBuilder

  /**
   * Adds a group to some numbered tiles specified.
   * @param group The group to add to those tiles.
   * @param number The numbers of the tiles to group.
   * @return this builder.
   */
  def withGroupedTiles(group: String, number: Int*): BoardBuilder

  /**
   * Adds a group to the tiles that belong to another group.
   * @param group The group of the tiles to select.
   * @param newGroup The group to add to selected tiles.
   * @return this builder.
   */
  def withGroupedTiles(group: String, newGroup: String): BoardBuilder

  /** Specifies the definition's disposition. */
  def withDisposition(disposition: Int => Disposition): BoardBuilder

  /** Completes the definition's building process. */
  def complete(): BoardDefinition

  /** Checks if the builder can complete the definition. */
  def isCompletable: Boolean
}

object BoardBuilder {

  private class BoardBuilderImpl() extends BoardBuilder {

    private var name: Option[String] = None
    private var from: Option[Int] = None
    private var totalTiles: Option[Int] = None
    private var nameMap: Map[Int, String] = Map()
    private var groupMap: Map[Int, Set[String]] = Map()
    private var disposition: Option[Int => Disposition] = None

    private var consumed = false

    override def withName(name: String): BoardBuilder = {
      this.name = Some(name)
      this
    }

    override def withNumberedTiles(total: Int, from: Int): BoardBuilder = {
      this.from = Some(from)
      this.totalTiles = Some(total)
      this
    }


    override def withNamedTile(number: Int, name: String): BoardBuilder = {
      nameMap = nameMap + (number -> name)
      this
    }

    override def withGroupedTiles(group: String, number: Int*): BoardBuilder = {
      this.assignGroup(group, number.toSeq)
      this
    }

    override def withGroupedTiles(group: String, newGroup: String): BoardBuilder = {
      this.assignGroup(newGroup, groupMap.filter(_._2.contains(group)).keys.toSeq)
      this
    }

    private def assignGroup(group: String, number: Seq[Int]): Unit =
      for (n <- number) {
        if (groupMap.contains(n)) {
          groupMap = groupMap + (n -> groupMap(n).+(group))
        } else {
          groupMap = groupMap + (n -> Set(group))
        }
      }

    override def withDisposition(disposition: Int => Disposition): BoardBuilder = {
      this.disposition = Some(disposition)
      this
    }

    override def complete(): BoardDefinition = {
      if (consumed) throw new IllegalStateException("This builder has already built a definition")
      val tileSet = (from.get to totalTiles.get).toList
        .map({
          case num if nameMap.contains(num) && groupMap.contains(num) => TileDefinition(num, nameMap(num), groupMap(num))
          case num if nameMap.contains(num) => TileDefinition(num, nameMap(num))
          case num if groupMap.contains(num) => TileDefinition(num, groupMap(num))
          case num => TileDefinition(num)
        }).toSet
      consumed = true
      BoardDefinition(name.get, tileSet, disposition.get(totalTiles.get))
    }

    override def isCompletable: Boolean = !consumed && from.isDefined && totalTiles.isDefined && name.isDefined && disposition.isDefined
  }

  /** Instantiates a definition builder. */
  def apply(): BoardBuilder = new BoardBuilderImpl()
}
