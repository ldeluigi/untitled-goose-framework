package model.entities.board

import model.Tile

trait TileDefinition {
  def number: Option[Int]

  def name: Option[String]

  def tileType: Option[List[String]]

  def next: Option[TileDefinition]
}


object TileDefinition {

  implicit def ordering[A <: TileDefinition]: Ordering[A] = (x: A, y: A) => {
    if (x.number.isDefined) {
      if (y.number.isDefined) {
        x.number.get compare y.number.get
      } else {
        ordering.compare(x, y.next.get)
      }
    } else {
      //If there is no number sure there is next
      //TODO make sure of this
      ordering.compare(x.next.get, y)
    }
  }

  private class TileDefinitionImpl(num: Int) extends TileDefinition {

    override def number: Option[Int] = Some(num)

    override def name: Option[String] = None

    override def tileType: Option[List[String]] = None

    override def next: Option[TileDefinition] = None

    override def equals(obj: Any): Boolean = {
      obj match {
        case t: TileDefinition => {
          if (number.isDefined && t.number.isDefined) {
            return number.get == t.number.get
          }

          if (name.isDefined && t.name.isDefined) {
            return name.get == t.name.get
          }

          false
        }
      }
    }
  }

  def apply(number: Int): TileDefinition = new TileDefinitionImpl(number) //TODO change this
}
