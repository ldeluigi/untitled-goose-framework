package untitled.goose.framework.dsl.events.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode

case class EventDefinitionsNode() extends RuleBookNode with EventCollection {
  private var definitions: List[EventNode] = List()

  override def isEventDefined(name: String): Boolean = definitions exists (_.name == name)

  override def defineEvent(event: EventNode): Unit = definitions :+= event

  override def check: Seq[String] = {
    (definitions groupBy (_.name) collect { case (x, List(_, _, _*)) => x } map
      ("Duplicate node definition: \"" + _ + "\"") toSeq) ++ definitions.flatMap(_.check)
  }

  override def getEvent(name: String): EventNode = definitions.find(_.name == name).get
}
