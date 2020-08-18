package model

trait TileIdentifier {
  def number: Option[Int]

  def name: Option[String]

  def groups: Set[String] //Group come riferimento alla classe dentro al GraphicDescriptor
}

object TileIdentifier {

  private class TileIdentifierImpl(val tileNum: Option[Int], val tileName: Option[String], val tileGroups: Set[String]) extends TileIdentifier {
    override def number: Option[Int] = tileNum

    override def name: Option[String] = tileName

    override def groups: Set[String] = tileGroups
  }

  def apply(number: Int): TileIdentifier = new TileIdentifierImpl(Some(number), None, Set())

  def apply(name: String): TileIdentifier = new TileIdentifierImpl(None, Some(name), Set())

  def apply(groups: Set[String]): TileIdentifier = new TileIdentifierImpl(None, None, groups)
}
