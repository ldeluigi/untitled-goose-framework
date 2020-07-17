package model.entities.board

import model.Tile

trait TileDefinition {
  def number: Option[Int]

  def name: Option[String]

  def tileType: Option[List[String]]
}


object TileDefinition {

  implicit def ordering[A <: TileDefinition]: Ordering[A] = (x: A, y: A) =>
    (x.number, y.number) match {
      case (None, None) => 0
      case (None, Some(_)) => 1
      case (Some(_), None) => -1
      case (Some(xNum), Some(yNum)) => xNum compare yNum
    }

  private class TileDefinitionImpl(num: Int) extends TileDefinition {

    override def number: Option[Int] = Some(num)

    override def name: Option[String] = None

    override def tileType: Option[List[String]] = None

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
