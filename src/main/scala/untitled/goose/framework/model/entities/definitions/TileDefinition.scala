package untitled.goose.framework.model.entities.definitions

import untitled.goose.framework.model.Groupable

/** Defines a tile on the definition, statically. */
trait TileDefinition extends Groupable {

  /** A tile can be numbered. */
  def number: Option[Int]

  /** A tile can have a name. */
  def name: Option[String]

  def ==(obj: TileDefinition): Boolean =
    (number.isDefined && obj.number.isDefined && number.get == obj.number.get) ||
      (name.isDefined && obj.name.isDefined && name.get == obj.name.get)

  override def equals(obj: Any): Boolean = {
    obj match {
      case t: TileDefinition => t == this
      case _ => false
    }
  }

  override def hashCode(): Int = number.getOrElse(name.get.hashCode) + 3

  override def toString: String =
    this.getClass.getSimpleName + "(" + this.name.getOrElse(this.number.get) + ")"
}

object TileDefinition {

  /** Tile order is based on reciprocal number order. */
  implicit def compare[A <: TileDefinition]: Ordering[A] = (x: A, y: A) =>
    (x.number, y.number) match {
      case (None, None) => 0
      case (None, Some(_)) => 1
      case (Some(_), None) => -1
      case (Some(xNum), Some(yNum)) => xNum compare yNum
    }

  private class TileDefinitionImpl(val number: Option[Int],
                                   val name: Option[String],
                                   val groups: Seq[String]) extends TileDefinition

  /** A factory that creates a tile definition based on a number. */
  def apply(number: Int): TileDefinition = new TileDefinitionImpl(Some(number), None, Seq())

  /** A factory that creates a tile definition based on a string. */
  def apply(name: String): TileDefinition = new TileDefinitionImpl(None, Some(name), Seq())

  /** A factory that creates a tile definition based on a number and a group specifying a certain property. */
  def apply(number: Int, groups: Seq[String]): TileDefinition =
    new TileDefinitionImpl(Some(number), None, groups)

  /** A factory that creates a tile definition based on a name and a group specifying a certain property. */
  def apply(name: String, groups: Seq[String]): TileDefinition =
    new TileDefinitionImpl(None, Some(name), groups)

  /** A factory that creates a tile definition based on a number, a name and a group specifying a certain property. */
  def apply(number: Int, name: String, groups: Seq[String] = Seq()): TileDefinition =
    new TileDefinitionImpl(Some(number), Some(name), groups)
}
