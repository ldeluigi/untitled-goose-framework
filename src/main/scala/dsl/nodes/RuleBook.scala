package dsl.nodes

import model.entities.board.BoardBuilder

trait RuleBook extends RuleBookNode {
  def boardBuilder: BoardBuilder

  def graphicMap: GraphicMapNode

  def setGameName(name: String): Unit
}

object RuleBook {

  private class RuleBookImpl() extends RuleBook {

    private val gameName: SingleValueNode[String] = new SingleValueNode("Game Name")

    val boardBuilder: BoardBuilder = BoardBuilder()

    val graphicMap: GraphicMapNode = new GraphicMapNode()

    def setGameName(name: String): Unit = {
      gameName value = name
      boardBuilder.withName(name)
    }

    override def toString: String = {
      gameName value
    }

    override def check: Seq[String] = {
      gameName.check ++
        (if (boardBuilder.isCompletable) Seq() else Seq("Board definition not complete")) ++
        graphicMap.check
    }
  }

  def apply(): RuleBook = new RuleBookImpl()
}
