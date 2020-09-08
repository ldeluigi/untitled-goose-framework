package untitled.goose.framework.dsl.dice.words

class CreateWord {

  def movementDice(name: String): DiceHavingWord[Int] = DiceHavingWord(name)

  def dice(name: String): DiceHavingWord[Any] = DiceHavingWord(name)
}
