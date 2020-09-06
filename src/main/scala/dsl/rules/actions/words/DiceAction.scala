package dsl.rules.actions.words

import dsl.nodes.RuleBook
import dsl.rules.actions.nodes.ActionRuleNode.{ActionRuleNode, DiceActionNode}
import model.entities.runtime.GameState

case class DiceAction(actionName: String, when: GameState => Boolean, allow: Boolean, diceNumber: Int, diceName: String, isMovement: Boolean)(implicit ruleBook: RuleBook) {
  def priority(priority: Int): ActionRuleNode = DiceActionNode(actionName, when, priority, allow, diceNumber, diceName, isMovement, ???)

  UnnamedDiceAction
}
