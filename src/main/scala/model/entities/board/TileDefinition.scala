package model.entities.board

trait TileDefinition {
  def number: Option[Int]

  def name: Option[String]

  def tileType: Option[List[String]]
}


object TileDefinition {

  implicit def compare[A <: TileDefinition]: Ordering[A] = (x: A, y: A) =>
    (x.number, y.number) match {
      case (None, None) => 0
      case (None, Some(_)) => 1
      case (Some(_), None) => -1
      case (Some(xNum), Some(yNum)) => xNum compare yNum
    }

  private class TileDefinitionImpl(val number: Option[Int], val name: Option[String]) extends TileDefinition {

    override def tileType: Option[List[String]] = None

    override def equals(obj: Any): Boolean = {
      obj match {
        case t: TileDefinition =>
          (number.isDefined && t.number.isDefined && number.get == t.number.get) ||
          (name.isDefined && t.name.isDefined && name.get == t.name.get)
        case _ => false
      }
    }
  }

  def apply(number: Int): TileDefinition = new TileDefinitionImpl(Some(number), None)

  def apply(name: String): TileDefinition = new TileDefinitionImpl(None, Some(name))

  def apply(number: Int, name: String): TileDefinition = new TileDefinitionImpl(Some(number), Some(name))
}
