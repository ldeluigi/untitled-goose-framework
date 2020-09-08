package untitled.goose.framework.dsl.dice.words

import untitled.goose.framework.dsl.nodes.RuleBook

class CreateWord {

  def movementDice(name: String)(implicit ruleBook: RuleBook): MovementDiceHavingWord = MovementDiceHavingWord(name)

  def dice(name: String)(implicit ruleBook: RuleBook): GenericDiceHavingWord = GenericDiceHavingWord(name)
}
