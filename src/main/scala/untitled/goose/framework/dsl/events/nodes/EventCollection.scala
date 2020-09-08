package untitled.goose.framework.dsl.events.nodes

trait EventCollection {
  def isEventDefined(name: String): Boolean

  //def getEvent(properties, state?) : CustomGameEvent
}
