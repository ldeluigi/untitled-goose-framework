package untitled.goose.framework.dsl.dice.words

import untitled.goose.framework.dsl.nodes.RuleBook

/** Used for "create [item definition]" */
class CreateWord {

  /** Enables "create movementDice [name] ... */
  def movementDice(name: String)(implicit ruleBook: RuleBook): MovementDiceHavingWord = MovementDiceHavingWord(name)

  /** Enables "create dice [name] ... */
  def dice(name: String)(implicit ruleBook: RuleBook): GenericDiceHavingWord = GenericDiceHavingWord(name)
}
