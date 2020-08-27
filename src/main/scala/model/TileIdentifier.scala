package model

import model.TileIdentifier.Group

trait TileIdentifier {

  def name: Option[String]

  def number: Option[Int]

  def group: Option[Group]

}

object TileIdentifier {
  case class Group(groupName: String)

  private class TileIdentifierImpl(val tileNum: Option[Int], val tileName: Option[String], val tileGroups: Option[Group]) extends TileIdentifier {

    override def number: Option[Int] = tileNum

    override def name: Option[String] = tileName

    override def group: Option[Group] = tileGroups

    //override della equals: due tileidentifier sono uguali se hanno lo stesso nome o numero o gruppo -> così posso usare la cointains
    override def equals(obj: Any): Boolean = {
      obj match {
        case obj: TileIdentifier =>
          this.name == obj.name || this.number == obj.number || this.group == obj.group
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
  def apply(group: Group): TileIdentifier = new TileIdentifierImpl(None, None, Some(group))

}
