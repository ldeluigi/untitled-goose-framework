package model.entities

/**
 * Models the dice concept.
 */
object Dice {

  trait Dice[DiceSide] {

    /**
     * @return the dice's name
     */
    def name: String

    /**
     * @return the dice's side, outcome of a dice roll
     */
    def roll: DiceSide
  }

  /** Models a dice's movement. */
  trait MovementDice extends Dice[Int]

  object Factory {

    /**
     * @param sides the dice's sides
     * @param name  the dice's name
     */
    private class RandomDice[DiceSide](sides: Set[DiceSide], val name: String) extends Dice[DiceSide] {
      override def roll: DiceSide = {
        val n = util.Random.nextInt(sides.size)
        sides.iterator.drop(n).next
      }
    }

    /** Models a dice's random movement. */
    private class RandomMovementDice(sideSet: Set[Int], diceName: String)
      extends RandomDice[Int](sideSet, diceName) with MovementDice

    /**
     * @param sides the dice's sides
     * @param name  the name of the random dice
     * @return the random dice
     */
    def random[DiceSide](sides: Set[DiceSide], name: String): Dice[DiceSide] = new RandomDice(sides, name)

    /**
     * @param sides the dice's sides
     * @param name  the name of the random dice
     * @return the random dice's movement
     */
    def randomMovement(sides: Set[Int], name: String): MovementDice = new RandomMovementDice(sides, name)

  }

}
