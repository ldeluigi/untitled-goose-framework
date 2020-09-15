package untitled.goose.framework.model.entities

import scala.util.Random

/** Models a dice. */
trait Dice[DiceSide] {

  /** The name of the dice. */
  def name: String

  /** Returns one face of the dice, at random. */
  def roll(): DiceSide
}

object Dice {

  /** Defines a movement dice. */
  trait MovementDice extends Dice[Int]

  object Factory {

    private class RandomDice[DiceSide](sides: Seq[DiceSide], val name: String) extends Dice[DiceSide] {
      override def roll(): DiceSide = Random.shuffle(sides).head
    }

    private class RandomMovementDice(sides: Seq[Int], diceName: String)
      extends RandomDice[Int](sides, diceName) with MovementDice

    /** Creates a dice that rolls randomly. */
    def random[DiceSide](sides: Seq[DiceSide], name: String): Dice[DiceSide] = new RandomDice(sides, name)

    /** Creates a movement dice that rolls randomly. */
    def randomMovement(sides: Seq[Int], name: String): MovementDice = new RandomMovementDice(sides, name)
  }

}
