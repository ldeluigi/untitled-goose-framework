package untitled.goose.framework.dsl.events.nodes

/**
 * This trait represent a collection of events from where it is possible to know whether or not
 * an event with the given name is defined and retrieve the instance
 */
trait EventDefinitionCollection {
  def isEventDefined(name: String): Boolean

  def defineEvent(event: EventNode): Unit

  def getEvent(name: String): EventNode

  def name: String
}
