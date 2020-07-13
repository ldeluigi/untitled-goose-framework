package model.entities.board

trait TileDefinition {
  def number: Option[Int]

  def name: Option[String]

  def tileType: Option[List[String]]

  def next: Option[TileDefinition]
}


object TileDefinition {
  def apply(number: Int) = MockTileDefinition(number) //TODO change this
}

case class MockTileDefinition(num: Int) extends TileDefinition {

  override def number: Option[Int] = Some(num)

  override def name: Option[String] = None

  override def tileType: Option[List[String]] = None

  override def next: Option[TileDefinition] = None
}