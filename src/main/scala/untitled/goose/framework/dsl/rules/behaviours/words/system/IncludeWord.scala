package untitled.goose.framework.dsl.rules.behaviours.words.system

import untitled.goose.framework.dsl.UtilityWords.SystemWord
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.rules.behaviours.BehaviourRule

/** Used to include pre-made items. */
class IncludeWord {

  /** Enables "Include these system [item]" */
  def these(systemWord: SystemWord): IncludeWord = this

  /** Enables "Include behaviours([behaviour], [behaviour] ...)" */
  def behaviours(behaviourRule: BehaviourRule*)(implicit ruleBook: RuleBook): Unit = {
    behaviourRule.foreach(ruleBook.ruleSet.behaviourCollectionNode.addSystemBehaviour)
  }
}
