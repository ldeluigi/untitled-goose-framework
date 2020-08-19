package model

import model.TileIdentifier.{Group}

trait TileIdentifier {

  def number: Option[Int]

  def name: Option[String]

  def groups: Option[Group]

}

object TileIdentifier {

  case class Group(val group: String){
  }

  private class TileIdentifierImpl(val tileNum: Option[Int], val tileName: Option[String], val tileGroups: Option[Group]) extends TileIdentifier {
    override def number: Option[Int] = tileNum

    override def name: Option[String] = tileName

    override def groups: Option[Group] = tileGroups
  }

  //override della equals: due tileidentifier sono uguali se hanno lo stesso nome o numero o gruppo -> così posso usare la cointains

  def apply(number: Int): TileIdentifier = new TileIdentifierImpl(Some(number), None, None)

  def apply(name: String): TileIdentifier = new TileIdentifierImpl(None, Some(name), None)

  def apply(groups: Group): TileIdentifier = new TileIdentifierImpl(None, None, Some(groups))
}


