package untitled.goose.framework.dsl.dice.words

object DiceType extends Enumeration {
  type DiceType = Value

  /** Dice type. */
  val movementDice, genericDice = Value
}
