package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.behaviours.words.SaveOrConsumeWord.{ConsumeWord, SaveWord}
import untitled.goose.framework.dsl.rules.behaviours.words.system.SystemBehavioursWords
import untitled.goose.framework.dsl.rules.operations.words.{ForEachWord, OperationWords}
import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime.{GameState, PlayerDefinition}
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

trait BehaviourWords extends OperationWords with SystemBehavioursWords {

  /** Creates a behaviour related CustomEventInstance (GameEvent) with given event name. */
  def gameEvent[T <: ConsumableGameEvent](name: String)(implicit ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    BehaviourCustomEventInstance.gameEvent(name, ruleBook)

  /** Creates a behaviour related CustomEventInstance (PlayerEvent) with given event name. */
  def playerEvent[T <: ConsumableGameEvent](name: String, player: GameState => PlayerDefinition)(implicit ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    BehaviourCustomEventInstance.playerEvent(name, player, ruleBook)

  /** Creates a behaviour related CustomEventInstance (TileEvent) with given event name. */
  def tileEvent[T <: ConsumableGameEvent](name: String, tile: GameState => TileDefinition)(implicit ruleBook: RuleBook): BehaviourCustomEventInstance[T] =
    BehaviourCustomEventInstance.tileEvent(name, tile, ruleBook)

  /** Enables "events [verb] ..." */
  def events[T <: ConsumableGameEvent : ClassTag]: EventsMatchingWord[T] = EventsMatchingWord()

  /** Enables "numberOf [filter] ..." */
  def numberOf[T <: ConsumableGameEvent : ClassTag](filterStrategy: FilterStrategy[T]): FilterStrategy[T] = filterStrategy

  /** Enables "all [subject] [filter]..." */
  def all[T <: ConsumableGameEvent : ClassTag](filterStrategy: T => Boolean): FilterStrategy[T] = FilterStrategy(filterStrategy)

  /** Enables "forEach [operation] ..." */
  val forEach: ForEachWord = ForEachWord()

  /** If used, it will make this behaviour consume its consumableEvent. */
  val consume: ConsumeWord = ConsumeWord()

  /** If used, it will make this behaviour save its consumableEvent to the history. */
  val save: SaveWord = SaveWord()
}
