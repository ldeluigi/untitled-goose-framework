package untitled.goose.framework.dsl.nodes

import untitled.goose.framework.dsl.board.nodes.{BoardBuilderNode, GraphicMapNode}
import untitled.goose.framework.dsl.dice.nodes.{DiceCollection, DiceManagerNode, DiceNode}
import untitled.goose.framework.dsl.events.nodes.EventDefinitionsNode

trait RuleBook extends RuleBookNode {
  def boardBuilder: BoardBuilderNode

  def graphicMap: GraphicMapNode

  def ruleSet: RuleSetNode

  def nodeDefinitions: EventDefinitionsNode

  def setGameName(name: String): Unit

  def diceCollection: DiceCollection

  def addDice(diceNode: DiceNode[_]): Unit
}

object RuleBook {

  private class RuleBookImpl() extends RuleBook {

    val diceCollection: DiceManagerNode = DiceManagerNode()

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
        diceCollection.check ++
        ruleSet.check
    }

    override def addDice(diceNode: DiceNode[_]): Unit = diceCollection.add(diceNode)
  }

  def apply(): RuleBook = new RuleBookImpl()
}
