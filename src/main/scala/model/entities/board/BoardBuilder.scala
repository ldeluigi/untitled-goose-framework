package model.entities.board


case class BoardBuilder() {

  private var name = ""
  private var from = 0
  private var totalTiles = 0
  private var nameMap: Map[Int, String] = Map()
  private var groupMap: Map[Int, Set[String]] = Map()
  private var disposition: Disposition = _


  def withName(name: String): BoardBuilder = {
    this.name = name
    this
  }

  def withNumberedTiles(total: Int, from: Int = 1): BoardBuilder = {
    this.from = from
    this.totalTiles = total
    this
  }

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

  def withDisposition(createDisposition: Int => Disposition): BoardBuilder = {
    this.disposition = createDisposition(totalTiles)
    this
  }

  def complete(): Board = {
    val tileSet = (from to totalTiles).toList
      .map({
        case num if nameMap.contains(num) && groupMap.contains(num) => TileDefinition(num, nameMap(num), groupMap(num))
        case num if nameMap.contains(num) => TileDefinition(num, nameMap(num))
        case num if groupMap.contains(num) => TileDefinition(num, groupMap(num))
        case num => TileDefinition(num)
      }).toSet
    Board(name, tileSet, disposition)
  }

}
