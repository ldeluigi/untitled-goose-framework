package untitled.goose.framework.model.events

import scala.reflect.ClassTag

// TODO scaladoc
class CustomGameEvent(val turn: Int, val cycle: Int, override val name: String) extends GameEvent {
  private var propertyMap: List[(Key[_], Option[_])] = List()

  override def toString: String = super.toString + "Properties: " + propertyMap

  def defineKey[T: ClassTag](key: String): Unit =
    if (!propertyMap.exists(_._1.equals(Key[T](key))))
      propertyMap ::= (Key[T](key) -> None)

  def getProperty[T: ClassTag](key: String): Option[T] = {
    propertyMap.find(_._1.equals(Key[T](key))).get._2.get match {
      case t: T => Some(t)
      case _ => throw new IllegalStateException(key + "this should never happen")
    }
  }

  def setProperty[T: ClassTag](key: String, value: T): Unit =
    if (propertyMap.exists(_._1.equals(Key[T](key)))) {
      propertyMap = propertyMap.filterNot(_._1.equals(Key[T](key)))
      propertyMap ::= (Key[T](key) -> Some(value))
    }
    else throw new IllegalArgumentException(key + " was never defined in this CustomEvent properties")
}

object CustomGameEvent {
  def apply(turn: Int, cycle: Int, name: String): CustomGameEvent = new CustomGameEvent(turn, cycle, name)
}
