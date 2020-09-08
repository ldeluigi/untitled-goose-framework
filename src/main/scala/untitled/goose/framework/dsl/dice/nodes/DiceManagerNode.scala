package untitled.goose.framework.dsl.dice.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.entities.Dice
import untitled.goose.framework.model.entities.Dice.MovementDice

case class DiceManagerNode() extends RuleBookNode with DiceCollection {
  var diceNodes: Seq[DiceNode[_]] = Seq()

  def add(diceNode: DiceNode[_]): Unit = diceNodes :+= diceNode

  override def check: Seq[String] =
    diceNodes.flatMap(_.check) ++
      (diceNodes groupBy (_.name) collect { case (x, List(_, _, _*)) => x } map
        ("Dice duplicate definition: \"" + _ + "\"") toSeq)

  override def isDiceDefined(name: String): Boolean = diceNodes.exists(_.name == name)

  override def isMovementDice(name: String): Boolean = diceNodes.exists {
    case DiceNode.GenericDiceNode(_, _) => false
    case DiceNode.MovementDiceNode(diceName, _) => diceName == name
  }

  override def getDice(name: String): Dice[Any] = diceNodes.find(_.name == name).get match {
    case d: DiceNode.GenericDiceNode[Any] => d.dice
  }

  override def getMovementDice(name: String): MovementDice = diceNodes.find(_.name == name).get match {
    case d: DiceNode.MovementDiceNode => d.dice
  }
}
