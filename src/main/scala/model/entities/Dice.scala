package model.entities

import scala.util.Random

object Dice {

  trait Dice[DiceSide] {

    def name: String

    def roll: DiceSide
  }

  trait MovementDice extends Dice[Int]

  object Factory {

    private class RandomDice[DiceSide](sides: Seq[DiceSide], val name: String) extends Dice[DiceSide] {
      override def roll: DiceSide = Random.shuffle(sides).head
    }

    private class RandomMovementDice(sides: Seq[Int], diceName: String)
      extends RandomDice[Int](sides, diceName) with MovementDice

    def random[DiceSide](sides: Seq[DiceSide], name: String): Dice[DiceSide] = new RandomDice(sides, name)

    def randomMovement(sides: Seq[Int], name: String): MovementDice = new RandomMovementDice(sides, name)

  }

}
