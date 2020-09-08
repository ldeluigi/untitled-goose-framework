package untitled.goose.framework.dsl.nodes

import untitled.goose.framework.dsl.board.nodes.{BoardBuilderNode, GraphicMapNode}
import untitled.goose.framework.dsl.events.nodes.EventDefinitionsNode

trait RuleBook extends RuleBookNode {
  def boardBuilder: BoardBuilderNode

  def graphicMap: GraphicMapNode

  def ruleSet: RuleSetNode

  def nodeDefinitions: EventDefinitionsNode

  def setGameName(name: String): Unit
}

object RuleBook {

  private class RuleBookImpl() extends RuleBook {

    private val gameName: SingleValueNode[String] = new SingleValueNode("Game Name")

    val boardBuilder: BoardBuilderNode = BoardBuilderNode()

    val graphicMap: GraphicMapNode = new GraphicMapNode(boardBuilder)

    val ruleSet: RuleSetNode = RuleSetNode(boardBuilder)

    val nodeDefinitions: EventDefinitionsNode = EventDefinitionsNode()

    def setGameName(name: String): Unit = {
      gameName value = name
      boardBuilder.withName(name)
    }

    override def check: Seq[String] = {
      gameName.check ++
        nodeDefinitions.check ++
        boardBuilder.check ++
        graphicMap.check ++
        ruleSet.check
    }
  }

  def apply(): RuleBook = new RuleBookImpl()
}
