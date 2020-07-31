package model.entities


trait Dice[DiceSide] {
  def name: String

  def roll: DiceSide

}

object Dice {

  private class RandomDice[DiceSide](sides: Set[DiceSide], val name: String) extends Dice[DiceSide] {
    override def roll: DiceSide = {
      val n = util.Random.nextInt(sides.size)
      sides.iterator.drop(n).next
    }
  }

  def apply[DiceSide](sides: Set[DiceSide], name: String): Dice[DiceSide] = new RandomDice(sides, name)

}