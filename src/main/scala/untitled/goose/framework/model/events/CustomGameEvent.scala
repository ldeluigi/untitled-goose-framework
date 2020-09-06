package untitled.goose.framework.model.events

import scala.reflect.ClassTag

class CustomGameEvent(val turn: Int, val cycle: Int, override val name: String) extends GameEvent {
  var propertyMap: Map[Key[_], Option[_]] = Map()

  override def toString: String = super.toString + "Properties: " + propertyMap

  def defineKey[T: ClassTag](keyName: String): Unit = propertyMap += (Key[T](keyName) -> None)

  def getProperty[T: ClassTag](key: String): Option[T] = propertyMap(Key[T](key)).get.asInstanceOf[Option[T]]

  def setProperty[T: ClassTag](key: String, value: T): Unit =
    if (propertyMap.keySet.contains(Key[T](key)))
      propertyMap += (Key[T](key) -> Some(value))
    else throw new IllegalArgumentException(key + " was never defined in this CustomEvent properties")
}

object CustomGameEvent {
  def apply(turn: Int, cycle: Int, name: String): CustomGameEvent = new CustomGameEvent(turn, cycle, name)
}
