package dsl.dice.words

trait DiceWords {

  val Create: CreateWord = new CreateWord

  def totalSides(total: Int): Seq[Int] = 1 to total

  def sides[T](values: T*): Seq[T] = values
}
