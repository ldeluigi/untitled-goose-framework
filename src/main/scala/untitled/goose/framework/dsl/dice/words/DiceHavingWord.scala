package untitled.goose.framework.dsl.dice.words

case class DiceHavingWord[T](name: String) {

  def having(sides: Seq[T]): Unit = ??? //TODO Generate a DiceNode
}
