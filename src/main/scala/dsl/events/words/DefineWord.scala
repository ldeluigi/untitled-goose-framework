package dsl.events.words

import dsl.events.nodes.EventNode
import dsl.nodes.RuleBook

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
