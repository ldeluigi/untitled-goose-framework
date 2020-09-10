package untitled.goose.framework.model.events

import scala.reflect.ClassTag

// TODO scaladoc
class CustomGameEvent(val turn: Int, val cycle: Int, override val name: String) extends GameEvent {
  private var propertyList: List[(Key[_], Option[_])] = List()

  override def toString: String = super.toString + "Properties: " + propertyList

  def getProperty[T: ClassTag](key: String): Option[T] = {
    propertyList.find(_._1.equals(Key[T](key))).get._2.get match {
      case t: T => Some(t)
      case _ => throw new IllegalStateException(key + "this should never happen")
    }
  }

  def setProperty[T: ClassTag](key: String, value: T): Unit = {
    propertyList = propertyList.filterNot(_._1.equals(Key[T](key)))
    propertyList ::= (Key[T](key) -> Some(value))
  }

}

object CustomGameEvent {
  def apply(turn: Int, cycle: Int, name: String): CustomGameEvent = new CustomGameEvent(turn, cycle, name)
}
