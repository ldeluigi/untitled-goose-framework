package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.dsl.events.nodes.EventDefinitionCollection
import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.events.words.CustomEventInstance.AbstractCustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.{GameState, Player, Tile}
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.events.{CustomGameEvent, CustomPlayerEvent, CustomTileEvent}

import scala.reflect.ClassTag

trait BehaviourCustomEventInstance[E <: ConsumableGameEvent] extends CustomEventInstance[(GameState, E)] {
  def +[T: ClassTag](name: String, value: (GameState, E) => T): BehaviourCustomEventInstance[E]
}

object BehaviourCustomEventInstance {

  private abstract class AbstractBehaviourCustomEventInstance[E <: ConsumableGameEvent](name: String,
                                                                                definedEvents: EventDefinitionCollection)
    extends AbstractCustomEventInstance[(GameState, E)](name, definedEvents) with BehaviourCustomEventInstance[E] {

    override def +[T: ClassTag](name: String, value: (GameState, E) => T): BehaviourCustomEventInstance[E] = {
      this.+(name, tuple => value(tuple._1, tuple._2))
      this
    }
  }

  def tileEvent[T <: ConsumableGameEvent](name: String, tile: GameState => Tile, ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    new AbstractBehaviourCustomEventInstance[T](name, ruleBook.eventDefinitions.tileEventCollection) {
      override def initEvent(input: (GameState, T)): CustomGameEvent =
        CustomTileEvent(input._1.currentTurn, input._1.currentCycle, name, tile(input._1))
    }

  def playerEvent[T <: ConsumableGameEvent](name: String, player: GameState => Player, ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    new AbstractBehaviourCustomEventInstance[T](name, ruleBook.eventDefinitions.playerEventCollection) {
      override def initEvent(input: (GameState, T)): CustomGameEvent =
        CustomPlayerEvent(input._1.currentTurn, input._1.currentCycle, name, player(input._1))
    }

  def gameEvent[T <: ConsumableGameEvent](name: String, ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    new AbstractBehaviourCustomEventInstance[T](name, ruleBook.eventDefinitions.gameEventCollection) {
      override def initEvent(input: (GameState, T)): CustomGameEvent =
        CustomGameEvent(input._1.currentTurn, input._1.currentCycle, name)
    }

}
