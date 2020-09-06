package dsl.dice.words

case class DiceHavingWord[T]() {

  def having(sides: Seq[T]): Unit = ???
}
