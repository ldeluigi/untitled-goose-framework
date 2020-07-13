package model.entities.board

trait Board {
  def tiles: List[TileDefinition]

  def name: String

  def disposition: Disposition
}
