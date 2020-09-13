package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.{GameState, Player, Tile}
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.events.{GameEvent, Key}

import scala.reflect.ClassTag

trait BehaviourCustomEventInstance[EventType <: ConsumableGameEvent] {
  def properties: Map[Key[_], (EventType, GameState) => Any]

  def generateEvent(state: GameState, event: EventType): GameEvent

  def +[T: ClassTag](name: String, value: GameState => T): BehaviourCustomEventInstance[EventType]

  def +[T: ClassTag](name: String, value: (EventType, GameState) => T): BehaviourCustomEventInstance[EventType]

  def name: String

  def check: Seq[String]
}

//TODO Delu
object BehaviourCustomEventInstance {

  private abstract class AbstractBehaviourCustomEventInstance[EventType <: ConsumableGameEvent] extends BehaviourCustomEventInstance[EventType] {
    override def properties: Map[Key[_], (EventType, GameState) => Any]

    def generateEvent(state: GameState, event: EventType): GameEvent = ???

    override def +[T: ClassTag](name: String, value: GameState => T): BehaviourCustomEventInstance[EventType] = ???

    override def +[T: ClassTag](name: String, value: (EventType, GameState) => T): BehaviourCustomEventInstance[EventType] = ???

    override def name: String = ???

    override def check: Seq[String] = ???
  }

  def tileEvent[T <: ConsumableGameEvent](name: String, tile: GameState => Tile, ruleBook: RuleBook): BehaviourCustomEventInstance[T] = ???

  def playerEvent[T <: ConsumableGameEvent](name: String, player: GameState => Player, ruleBook: RuleBook): BehaviourCustomEventInstance[T] = ???

  def gameEvent[T <: ConsumableGameEvent](name: String, ruleBook: RuleBook): BehaviourCustomEventInstance[T] = ???

}
