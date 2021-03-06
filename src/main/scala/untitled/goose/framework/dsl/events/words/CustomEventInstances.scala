package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.events.words.CustomEventInstance.AbstractCustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime.{GameState, PlayerDefinition}
import untitled.goose.framework.model.events.{CustomGameEvent, CustomPlayerEvent, CustomTileEvent}

/** Factory for custom event instances. */
object CustomEventInstances {

  /** Creates a custom event instance related to given gameEvent. */
  def gameEvent(name: String, ruleBook: RuleBook): CustomEventInstance[GameState] =
    new AbstractCustomEventInstance[GameState](name, ruleBook.eventDefinitions.gameEventCollection) {
      override def initEvent(input: GameState): CustomGameEvent =
        CustomGameEvent(input.currentTurn, input.currentCycle, name)
    }

  /** Creates a custom event instance related to given playerEvent. */
  def playerEvent(name: String, player: GameState => PlayerDefinition, ruleBook: RuleBook): CustomEventInstance[GameState] =
    new AbstractCustomEventInstance[GameState](name, ruleBook.eventDefinitions.playerEventCollection) {
      override def initEvent(input: GameState): CustomGameEvent =
        CustomPlayerEvent(input.currentTurn, input.currentCycle, name, player(input))
    }

  /** Creates a custom event instance related to given tileEvent. */
  def tileEvent(name: String, tile: GameState => TileDefinition, ruleBook: RuleBook): CustomEventInstance[GameState] =
    new AbstractCustomEventInstance[GameState](name, ruleBook.eventDefinitions.tileEventCollection) {
      override def initEvent(input: GameState): CustomGameEvent =
        CustomTileEvent(input.currentTurn, input.currentCycle, name, tile(input))
    }
}