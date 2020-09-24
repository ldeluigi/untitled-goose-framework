package untitled.goose.framework.dsl.board.nodes


/**
 * This trait represent a collection of identifiers that can answer whether or not an identifier is defined.
 */
trait TileIdentifiersCollection {

  def containsName(name: String): Boolean

  def containsGroup(group: String): Boolean

  def containsNumber(num: Int): Boolean
}
