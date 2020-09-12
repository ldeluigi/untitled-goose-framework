package untitled.goose.framework.dsl.events.nodes

trait EventDefinitionCollection {
  def isEventDefined(name: String): Boolean

  def defineEvent(event: EventNode): Unit

  def getEvent(name: String): EventNode

  def name: String
}