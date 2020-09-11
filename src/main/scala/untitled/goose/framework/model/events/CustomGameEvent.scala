package untitled.goose.framework.model.events

import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.language.dynamics
import scala.reflect.ClassTag

// TODO scaladoc & reimplement with dynamic features
class CustomGameEvent(val turn: Int, val cycle: Int, override val name: String) extends ConsumableGameEvent with Dynamic {
  private var propertyList: Map[String, Any] = Map()

  override def toString: String = super.toString + " Properties: " + propertyList

  def getProperty[T: ClassTag](key: String): Option[T] =
    propertyList.get(key).flatMap({
      case x: T => Some(x)
      case _ => None
    })

  def setProperty(key: String, value: Any): Unit = propertyList += key -> value

}

object CustomGameEvent {
  def apply(turn: Int, cycle: Int, name: String): CustomGameEvent = new CustomGameEvent(turn, cycle, name)
}
