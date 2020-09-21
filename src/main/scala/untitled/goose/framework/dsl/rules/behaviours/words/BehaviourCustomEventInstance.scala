package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.dsl.events.nodes.EventDefinitionCollection
import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.events.words.CustomEventInstance.AbstractCustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.{GameState, Player, Tile}
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.events.{CustomGameEvent, CustomPlayerEvent, CustomTileEvent}

import scala.reflect.ClassTag

trait BehaviourCustomEventInstance[E <: ConsumableGameEvent] extends CustomEventInstance[(E, GameState)] {
  def :+[T: ClassTag](name: String, value: (E, GameState) => T): BehaviourCustomEventInstance[E]
}

object BehaviourCustomEventInstance {

  private abstract class AbstractBehaviourCustomEventInstance[E <: ConsumableGameEvent](name: String,
                                                                                        definedEvents: EventDefinitionCollection)
    extends AbstractCustomEventInstance[(E, GameState)](name, definedEvents) with BehaviourCustomEventInstance[E] {

    override def :+[T: ClassTag](name: String, value: (E, GameState) => T): BehaviourCustomEventInstance[E] = {
      this.:+(name, (tuple: (E, GameState)) => value(tuple._1, tuple._2))
      this
    }
  }

  def tileEvent[T <: ConsumableGameEvent](name: String, tile: GameState => Tile, ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    new AbstractBehaviourCustomEventInstance[T](name, ruleBook.eventDefinitions.tileEventCollection) {
      override def initEvent(input: (T, GameState)): CustomGameEvent =
        CustomTileEvent(input._2.currentTurn, input._2.currentCycle, name, tile(input._2))
    }

  def playerEvent[T <: ConsumableGameEvent](name: String, player: GameState => Player, ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    new AbstractBehaviourCustomEventInstance[T](name, ruleBook.eventDefinitions.playerEventCollection) {
      override def initEvent(input: (T, GameState)): CustomGameEvent =
        CustomPlayerEvent(input._2.currentTurn, input._2.currentCycle, name, player(input._2))
    }

  def gameEvent[T <: ConsumableGameEvent](name: String, ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    new AbstractBehaviourCustomEventInstance[T](name, ruleBook.eventDefinitions.gameEventCollection) {
      override def initEvent(input: (T, GameState)): CustomGameEvent =
        CustomGameEvent(input._2.currentTurn, input._2.currentCycle, name)
    }

}
