package model.entities.board

trait TileDefinition {
  def number: Option[Int]

  def name: Option[String]

  def tileType: Option[List[String]]

  def next: Option[TileDefinition]
}
