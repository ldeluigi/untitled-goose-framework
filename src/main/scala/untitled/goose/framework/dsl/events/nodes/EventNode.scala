package untitled.goose.framework.dsl.events.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.events.Key


trait EventNode extends RuleBookNode {
  def properties: Set[Key[_]]

  def name: String

  def defineProperty[T](key: Key[T]): Unit

  def isPropertyDefined[T](key: Key[T]): Boolean
}

object EventNode {

  private class EventNodeImpl(override val name: String) extends EventNode {

    private var props: List[Key[_]] = List()

    override def check: Seq[String] = {
      props.groupBy(identity)
        .collect({ case (x, List(_, _, _*)) => x })
        .map("Multiple definitions of event property: \"" + _ + "\"")
        .toSeq
    }

    override def defineProperty[T](key: Key[T]): Unit = {
      props :+= key
    }

    override def isPropertyDefined[T](key: Key[T]): Boolean = props contains key

    override def properties: Set[Key[_]] = props.toSet
  }

  def apply(definitionName: String): EventNode = new EventNodeImpl(definitionName)
}
