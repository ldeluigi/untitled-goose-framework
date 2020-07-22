package model.actions

import engine.events.EventSink

trait Action {

  def name: String

  def execute(sink: EventSink): EventSink

  def updateState: Unit

  override def equals(obj: Any): Boolean = obj match {
    case a: Action if a.name == this.name => true
    case _ => false
  }

}
