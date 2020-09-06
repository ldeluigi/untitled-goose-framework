package untitled.goose.framework.dsl.board.nodes

trait TileIdentifiersCollection {

  def containsName(name: String): Boolean

  def containsGroup(group: String): Boolean

  def containsNumber(num: Int): Boolean
}
