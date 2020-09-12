package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.events.nodes.EventNode
import untitled.goose.framework.dsl.nodes.RuleBook

case class DefineWord() {

  def event(name: String)(implicit ruleBook: RuleBook): EventPropertiesWord = {
    val eventNode = EventNode(name)
    ruleBook.eventDefinitions.gameEventCollection.defineEvent(eventNode)
    EventPropertiesWord(eventNode)
  }

  def playerEvent(name: String)(implicit ruleBook: RuleBook): EventPropertiesWord = {
    val eventNode = EventNode(name)
    ruleBook.eventDefinitions.playerEventCollection.defineEvent(eventNode)
    EventPropertiesWord(eventNode)
  }

  def tileEvent(name: String)(implicit ruleBook: RuleBook): EventPropertiesWord = {
    val eventNode = EventNode(name)
    ruleBook.eventDefinitions.tileEventCollection.defineEvent(eventNode)
    EventPropertiesWord(eventNode)
  }
}
