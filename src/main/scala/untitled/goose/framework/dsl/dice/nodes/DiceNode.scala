package untitled.goose.framework.dsl.dice.nodes


import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.entities.Dice
import untitled.goose.framework.model.entities.Dice.MovementDice

/**
 * This node contains all the information needed to create a Dice instance.
 *
 * @tparam T the type of the dice sides
 */
sealed trait DiceNode[T] extends RuleBookNode {
  def name: String

  def sides: Seq[T]

  override def check: Seq[String] = Seq()

}

object DiceNode {

  case class GenericDiceNode[T](name: String, sides: Seq[T]) extends DiceNode[T] {
    def dice: Dice[T] = Dice.Factory.random(sides, name)
  }

  case class MovementDiceNode(name: String, sides: Seq[Int]) extends DiceNode[Int] {
    def dice: MovementDice = Dice.Factory.randomMovement(sides, name)
  }

}
