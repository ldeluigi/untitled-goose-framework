package untitled.goose.framework.dsl.rules.actions.words.dice

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleNode.{ActionRuleNode, DiceActionNode}
import untitled.goose.framework.model.entities.runtime.GameState

/**
 * Creates a dice roll action.
 * @param actionName the name of the action.
 * @param when the condition on which this action should be activated.
 * @param diceNumber the number of dices to throw at the same time.
 * @param diceName the name of the dice to throw. Must be previously defined.
 * @param isMovement if true, the dice will trigger movement events.
 * @param ruleBook the rulebook of reference.
 */
case class DiceAction(actionName: String, when: GameState => Boolean, diceNumber: Int, diceName: String, isMovement: Boolean)(implicit ruleBook: RuleBook) {

  /** Enables "[action] priority [number]" */
  def priority(priority: Int): ActionRuleNode = DiceActionNode(actionName, when, priority, diceNumber, diceName, isMovement, ruleBook.diceCollection)
}
