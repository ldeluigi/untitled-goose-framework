package dsl.nodes

import model.entities.board.BoardBuilder

trait RuleBook extends RuleBookNode {
  def boardBuilder: BoardBuilder

  def graphicMap: GraphicMapNode

  def setGameName(name: String): Unit
}

object RuleBook {

  private class RuleBookImpl() extends RuleBook {

    private val gameName: SingleValueNode[String] = new SingleValueNode()

    val boardBuilder: BoardBuilder = BoardBuilder()

    val graphicMap: GraphicMapNode = new GraphicMapNode()

    def setGameName(name: String): Unit = {
      gameName value = name
    }

    override def toString: String = {
      gameName value
    }

    override def check: Boolean = {
      gameName.check
    }
  }

  def apply(): RuleBook = new RuleBookImpl()
}
