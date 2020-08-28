package dsl.nodes

trait RuleSetNode extends RuleBookNode {
  def setPlayerRange(range: Range)
}

object RuleSetNode {

  class RuleSetNodeImpl(identifiers: DefinedTileIdentifiers) extends RuleSetNode {

    private val playerRange: SingleValueNode[Range] = new SingleValueNode("Number of players")

    override def setPlayerRange(range: Range): Unit = playerRange.value = range

    override def check: Seq[String] =
      playerRange.check

  }

  def apply(identifiers: DefinedTileIdentifiers): RuleSetNode = new RuleSetNodeImpl(identifiers)
}
