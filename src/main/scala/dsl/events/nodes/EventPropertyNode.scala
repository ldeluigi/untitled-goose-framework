package dsl.events.nodes

import dsl.nodes.RuleBookNode

//TODO is this even necessary?
case class EventPropertyNode() extends RuleBookNode {
  override def check: Seq[String] = ???
}
