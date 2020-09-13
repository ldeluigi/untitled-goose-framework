package untitled.goose.framework.dsl.rules.behaviours.words.system

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.rules.behaviours.BehaviourRule

class IncludeWord {

  def these(systemWord: SystemWord): IncludeWord = this

  def behaviours(behaviourRule: BehaviourRule*)(implicit ruleBook: RuleBook): Unit = {
    behaviourRule.foreach(ruleBook.ruleSet.behaviourCollectionNode.addSystemBehaviour)
  }
}
