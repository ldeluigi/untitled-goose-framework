package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.events.nodes.EventNode
import untitled.goose.framework.dsl.nodes.RuleBook

case class DefineWord() {

  def event(name: String)(implicit ruleBook: RuleBook): EventPropertiesWord = {
    val eventNode = EventNode(name, EventType.gameEvent)
    //TODO Add to rulebook
    EventPropertiesWord(eventNode)
  }

  def playerEvent(name: String)(implicit ruleBook: RuleBook): EventPropertiesWord = {
    val eventNode = EventNode(name, EventType.playerEvent)
    //TODO Add to rulebook
    EventPropertiesWord(eventNode)
  }

  def tileEvent(name: String)(implicit ruleBook: RuleBook): EventPropertiesWord = {
    val eventNode = EventNode(name, EventType.tileEvent)
    //TODO Add to rulebook
    EventPropertiesWord(eventNode)
  }

}
