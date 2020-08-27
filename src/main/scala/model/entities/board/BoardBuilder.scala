package model.entities.board

case class BoardBuilder() {

  private var name: Option[String] = None
  private var from: Option[Int] = None
  private var totalTiles: Option[Int] = None
  private var nameMap: Map[Int, String] = Map()
  private var groupMap: Map[Int, Set[String]] = Map()
  private var disposition: Option[Int => Disposition] = None

  /** Specifies a board's name.
   *
   * @param name the name of the board
   * @return the partial board builder object
   */
  def withName(name: String): BoardBuilder = {
    this.name = Some(name)
    this
  }

  /** Specifies the board's tile numeration.
   *
   * @param total the global number of tiles in the game
   * @param from  the initial tile index
   * @return the partial board builder object
   */
  def withNumberedTiles(total: Int, from: Int = 1): BoardBuilder = {
    this.from = Some(from)
    this.totalTiles = Some(total)
    this
  }

  /** Specifies the tile's name
   *
   * @param number the tile's index to name
   * @param name   the name to set to the specified tile
   * @return the partial board builder object
   */
  def withNamedTile(number: Int, name: String): BoardBuilder = {
    nameMap = nameMap + (number -> name)
    this
  }

  def withGroupedTiles(group: String, number: Int*): BoardBuilder = {
    for (n <- number) {
      if (groupMap.contains(n)) {
        groupMap = groupMap + (n -> groupMap(n).+(group))
      } else {
        groupMap = groupMap + (n -> Set(group))
      }
    }
    this
  }

  /** Specifies the board's disposition.
   *
   * @param disposition a certain disposition type
   * @return the partial board builder object
   */
  def withDisposition(disposition: Int => Disposition): BoardBuilder = {
    this.disposition = Some(disposition)
    this
  }

  /** Completes the board's building process.
   *
   * @return the parameter-complete board
   */
  def complete(): Board = {
    val tileSet = (from.get to totalTiles.get).toList
      .map({
        case num if nameMap.contains(num) && groupMap.contains(num) => TileDefinition(num, nameMap(num), groupMap(num))
        case num if nameMap.contains(num) => TileDefinition(num, nameMap(num))
        case num if groupMap.contains(num) => TileDefinition(num, groupMap(num))
        case num => TileDefinition(num)
      }).toSet
    Board(name.get, tileSet, disposition.get(totalTiles.get))
  }

}
