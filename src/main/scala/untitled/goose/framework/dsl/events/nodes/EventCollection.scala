package untitled.goose.framework.dsl.events.nodes

trait EventCollection {
  def isEventDefined(name: String): Boolean

  //TODO solve how to get a custom Event instance
  //def getEvent(properties, state?) : CustomGameEvent
}
