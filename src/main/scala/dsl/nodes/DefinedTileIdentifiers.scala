package dsl.nodes

trait DefinedTileIdentifiers {

  def containsName(name: String): Boolean

  def containsGroup(group: String): Boolean

  def containsNumber(num: Int): Boolean
}
