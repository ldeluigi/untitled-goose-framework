package untitled.goose.framework.model.events

import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

// TODO scaladoc & reimplement with dynamic features
class CustomGameEvent(val turn: Int, val cycle: Int, override val name: String) extends ConsumableGameEvent {
  private var propertyList: List[(Key[_], _)] = List()

  override def toString: String = super.toString + " Properties: " + propertyList

  def getProperty[T: ClassTag](key: String): Option[T] = {
    propertyList.find(_._1.equals(Key[T](key))).flatMap(_._2 match {
      case t: T => Some(t)
      case _ => None
    })
  }

  def setProperty[T: ClassTag](key: String, value: T): Unit = {
    propertyList = propertyList.filterNot(_._1.equals(Key[T](key)))
    propertyList ::= (Key[T](key) -> value)
  }

}

object CustomGameEvent {
  def apply(turn: Int, cycle: Int, name: String): CustomGameEvent = new CustomGameEvent(turn, cycle, name)
}
