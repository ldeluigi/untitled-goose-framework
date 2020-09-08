package untitled.goose.framework.dsl.rules.actions.words.dice

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleNode.{ActionRuleNode, DiceActionNode}
import untitled.goose.framework.model.entities.runtime.GameState

case class DiceAction(actionName: String, when: GameState => Boolean, allow: Boolean, diceNumber: Int, diceName: String, isMovement: Boolean)(implicit ruleBook: RuleBook) {
  def priority(priority: Int): ActionRuleNode = DiceActionNode(actionName, when, priority, allow, diceNumber, diceName, isMovement, ???)

  //TODO missing diceCollection to be retrieved from implicit rulebook
}
