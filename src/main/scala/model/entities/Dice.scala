package model.entities

object Dice {

  trait Dice[DiceSide] {

    def name: String

    def roll: DiceSide
  }

  trait MovementDice extends Dice[Int]

  object Factory {

    private class RandomDice[DiceSide](sides: Set[DiceSide], val name: String) extends Dice[DiceSide] {
      override def roll: DiceSide = {
        val n = util.Random.nextInt(sides.size)
        sides.iterator.drop(n).next
      }
    }

    private class RandomMovementDice(sideSet: Set[Int], diceName: String)
      extends RandomDice[Int](sideSet, diceName) with MovementDice

    def random[DiceSide](sides: Set[DiceSide], name: String): Dice[DiceSide] = new RandomDice(sides, name)

    def randomMovement(sides: Set[Int], name: String): MovementDice = new RandomMovementDice(sides, name)

  }

}
