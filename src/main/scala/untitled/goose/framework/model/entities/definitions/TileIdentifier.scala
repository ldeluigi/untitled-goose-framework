package untitled.goose.framework.model.entities.definitions

/** This entity identifies one or more tiles. */
trait TileIdentifier {

  /** If present, this identifies tiles with that name. */
  def name: Option[String]

  /** If present, this identifies tiles with that number. */
  def number: Option[Int]

  /** If present, this identifies tiles with that group. */
  def group: Option[String]

  override def toString: String =
    if (number.isDefined)
      number.get.toString
    else if (name.isDefined)
      name.get
    else if (group.isDefined)
      "Group(" + group.get + ")"
    else
      "Empty"

  override def hashCode(): Int = 1453 +
    13 * name.map(_.hashCode).getOrElse(1) +
    17 * number.map(_.hashCode).getOrElse(1) +
    19 * group.map(_.hashCode).getOrElse(1)

}

object TileIdentifier {

  private class TileIdentifierImpl(val tileNum: Option[Int],
                                   val tileName: Option[String],
                                   val tileGroups: Option[String]) extends TileIdentifier {

    override def number: Option[Int] = tileNum

    override def name: Option[String] = tileName

    override def group: Option[String] = tileGroups

    override def equals(obj: Any): Boolean =
      obj match {
        case obj: TileIdentifier =>
          if (this.name.isDefined && obj.name.isDefined) {
            this.name.get == obj.name.get
          } else if (this.number.isDefined && obj.number.isDefined) {
            this.number.get == obj.number.get
          } else if (this.group.isDefined && obj.group.isDefined) {
            this.group.get == obj.group.get
          } else {
            false
          }
      }
  }

  /** A factory to create a new TileIdentifier, based on the tile's number.
   *
   * @param number the identifying number
   * @return the newly created TileIdentifier object
   */
  def apply(number: Int): TileIdentifier = new TileIdentifierImpl(Some(number), None, None)

  /** A factory to create a new TileIdentifier, based on the tile's name.
   *
   * @param name the identifying name
   * @return the newly created TileIdentifier object
   */
  def apply(name: String): TileIdentifier = new TileIdentifierImpl(None, Some(name), None)

  /** A factory to create a new TileIdentifier, based on the tile's group.
   *
   * @param group the identifying group
   * @return the newly created TileIdentifier object
   */
  def apply(group: Group): TileIdentifier = new TileIdentifierImpl(None, None, Some(group.name))

  /** Utility class used to distinguish names and groups in apply factory. */
  case class Group(name: String)

  /** Implements the check method to check if a tile is identified. */
  implicit class IdentifierCheck(id: TileIdentifier) {
    /** Checks if this tile identifier would match the given TileDefinition. */
    def check(tile: TileDefinition): Boolean =
      if (id.name.isDefined && tile.name.isDefined) {
        tile.name.get == id.name.get
      } else if (id.number.isDefined && tile.number.isDefined) {
        tile.number.get == id.number.get
      } else if (id.group.isDefined) {
        tile.groups.contains(id.group.get)
      } else {
        false
      }
  }

}
