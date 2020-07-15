package model.entities.board

trait Board {
  def tiles: List[TileDefinition]

  def name: String

  def disposition: Disposition
}

object Board {
  def apply() = BoardImpl() //TODO change
}

case class BoardImpl() extends Board {
  override def tiles: List[TileDefinition] = List.range(0, 10).map(i => TileDefinition(i))

  override def name: String = "MockBoard"

  override def disposition: Disposition = ???
}