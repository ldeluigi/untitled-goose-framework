package untitled.goose.framework.dsl.events.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.{CustomGameEvent, GameEvent, Key}

case class EventNode(name: String) extends RuleBookNode {

  private var props: List[Key[_]] = List()

  override def check: Seq[String] = {
    props.groupBy(identity).collect { case (x, List(_, _, _*)) => x }
      .map("Multiple definitions of event property: " + _).toSeq
  }

  def addProperty[T](key: Key[T]): Unit = {
    props :+= key
  }

  def isPropertyDefined[T](key: Key[T]): Boolean = props contains key

  def generateEvent(properties: Map[Key[_], Any]): GameState => GameEvent = { state => {
      val c = CustomGameEvent(state.currentTurn, state.currentCycle, name)
      properties foreach { p =>
        c.setProperty(p._1.keyName, p._1.classTag.unapply(p._2))
      }
      c
    }
  }

}
