package model.rules

import engine.events.EventSink
import model.MatchState

trait ActionRule {

  def allowedActions(state: MatchState): Set[ActionAvailability]

}

object ActionRule {

  private class ActionRuleImpl() extends ActionRule {
    override def allowedActions(state: MatchState): Set[ActionAvailability] = Set()
  }

  def apply(): ActionRule = new ActionRuleImpl()
}
