package model.entities.board

import model.Groupable

/**
 * Models the concept of a single tile.
 */
trait TileDefinition extends Groupable {

  /** Number identifying a certain tile.
   *
   * @return the tile's index, if present
   */
  def number: Option[Int]

  /** Name identifying a certain tile.
   *
   * @return the tile's name, if present
   */
  def name: Option[String]
}

object TileDefinition {

  implicit def compare[A <: TileDefinition]: Ordering[A] = (x: A, y: A) =>
    (x.number, y.number) match {
      case (None, None) => 0
      case (None, Some(_)) => 1
      case (Some(_), None) => -1
      case (Some(xNum), Some(yNum)) => xNum compare yNum
    }

  private class TileDefinitionImpl(val number: Option[Int], val name: Option[String], val groups: Set[String]) extends TileDefinition {

    override def equals(obj: Any): Boolean = {
      obj match {
        case t: TileDefinition =>
          (number.isDefined && t.number.isDefined && number.get == t.number.get) ||
            (name.isDefined && t.name.isDefined && name.get == t.name.get)
        case _ => false
      }
    }
  }

  /** A factory that creates a tile definition based on a number. */
  def apply(number: Int): TileDefinition = new TileDefinitionImpl(Some(number), None, Set())

  /** A factory that creates a tile definition based on a string. */
  def apply(name: String): TileDefinition = new TileDefinitionImpl(None, Some(name), Set())

  /** A factory that creates a tile definition based on a number and a group specifying a certain property. */
  def apply(number: Int, groups: Set[String]): TileDefinition = new TileDefinitionImpl(Some(number), None, groups)

  /** A factory that creates a tile definition based on a name and a group specifying a certain property. */
  def apply(name: String, groups: Set[String]): TileDefinition = new TileDefinitionImpl(None, Some(name), groups)

  /** A factory that creates a tile definition based on a number, a name and a group specifying a certain property. */
  def apply(number: Int, name: String, groups: Set[String] = Set()): TileDefinition = new TileDefinitionImpl(Some(number), Some(name), groups)
}
