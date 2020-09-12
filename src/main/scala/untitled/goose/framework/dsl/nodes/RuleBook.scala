package untitled.goose.framework.dsl.nodes

import untitled.goose.framework.dsl.board.nodes.{BoardBuilderNode, GraphicMapNode}
import untitled.goose.framework.dsl.dice.nodes.{DiceCollection, DiceManagerNode}
import untitled.goose.framework.dsl.events.nodes.GooseEventCollectionsNode

trait RuleBook extends RuleBookNode {
  def boardBuilder: BoardBuilderNode

  def graphicMap: GraphicMapNode

  def ruleSet: RuleSetNode

  def eventDefinitions: GooseEventCollectionsNode

  def setGameName(name: String): Unit

  def diceCollection: DiceCollection
}

object RuleBook {

  private class RuleBookImpl() extends RuleBook {

    val diceCollection: DiceManagerNode = DiceManagerNode()

    private val gameName: SingleValueNode[String] = new SingleValueNode("Game Name")

    val boardBuilder: BoardBuilderNode = BoardBuilderNode()

    val graphicMap: GraphicMapNode = new GraphicMapNode(boardBuilder)

    val ruleSet: RuleSetNode = RuleSetNode(boardBuilder)

    val eventDefinitions: GooseEventCollectionsNode = GooseEventCollectionsNode()

    def setGameName(name: String): Unit = {
      gameName.value = name
      boardBuilder.withName(name)
    }

    override def check: Seq[String] = {
      gameName.check ++
        eventDefinitions.check ++
        boardBuilder.check ++
        graphicMap.check ++
        diceCollection.check ++
        ruleSet.check
    }

  }

  def apply(): RuleBook = new RuleBookImpl()
}
