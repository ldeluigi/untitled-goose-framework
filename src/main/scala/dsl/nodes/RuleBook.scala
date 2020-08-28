package dsl.nodes

trait RuleBook extends RuleBookNode {
  def boardBuilder: BoardBuilderNode

  def graphicMap: GraphicMapNode

  def setGameName(name: String): Unit
}

object RuleBook {

  private class RuleBookImpl() extends RuleBook {

    private val gameName: SingleValueNode[String] = new SingleValueNode("Game Name")

    val boardBuilder: BoardBuilderNode = BoardBuilderNode()

    val graphicMap: GraphicMapNode = new GraphicMapNode(boardBuilder)

    def setGameName(name: String): Unit = {
      gameName value = name
      boardBuilder.withName(name)
    }

    override def toString: String = {
      gameName value
    }

    override def check: Seq[String] = {
      gameName.check ++
        boardBuilder.check ++
        graphicMap.check
    }
  }

  def apply(): RuleBook = new RuleBookImpl()
}
