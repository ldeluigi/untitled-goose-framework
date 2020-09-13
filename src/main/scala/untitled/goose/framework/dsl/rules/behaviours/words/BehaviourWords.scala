package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.behaviours.words.SaveOrConsumeWord.{ConsumeWord, SaveWord}
import untitled.goose.framework.dsl.rules.behaviours.words.system.SystemBehavioursWords
import untitled.goose.framework.dsl.rules.operations.words.{ForEachWord, OperationWords}
import untitled.goose.framework.model.entities.runtime.{GameState, Player, Tile}
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

trait BehaviourWords extends OperationWords with SystemBehavioursWords {

  def customBehaviourGameEvent[T <: ConsumableGameEvent](name: String)(implicit ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    BehaviourCustomEventInstance.gameEvent(name, ruleBook)

  def customBehaviourPlayerEvent[T <: ConsumableGameEvent](name: String, player: GameState => Player)(implicit ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    BehaviourCustomEventInstance.playerEvent(name, player, ruleBook)

  def customBehaviourTileEvent[T <: ConsumableGameEvent](name: String, tile: GameState => Tile)(implicit ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    BehaviourCustomEventInstance.tileEvent(name, tile, ruleBook)

  def events[T <: ConsumableGameEvent : ClassTag]: EventsMatchingWord[T] = EventsMatchingWord()

  def numberOf[T <: ConsumableGameEvent : ClassTag](filterStrategy: FilterStrategy[T]): FilterStrategy[T] = filterStrategy

  def all[T <: ConsumableGameEvent : ClassTag](filterStrategy: T => Boolean): FilterStrategy[T] = FilterStrategy(filterStrategy)

  val forEach: ForEachWord = ForEachWord()

  val consume: ConsumeWord = ConsumeWord()

  val save: SaveWord = SaveWord()
}
