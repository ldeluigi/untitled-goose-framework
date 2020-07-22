package model.entities.board

trait Dice {

  def roll: Integer

}

object Dice {

  private class DiceImpl extends Dice {
    override def roll: Integer = scala.util.Random.between(1, 6)
  }

  def apply(): Dice = new DiceImpl()
}
