package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.events.words.CustomEventInstance.AbstractCustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.{GameState, Player, Tile}
import untitled.goose.framework.model.events.{CustomGameEvent, CustomPlayerEvent, CustomTileEvent}

object CustomEventInstances {

  def gameEvent(name: String, ruleBook: RuleBook): CustomEventInstance[GameState] =
    new AbstractCustomEventInstance[GameState](name, ruleBook.eventDefinitions.gameEventCollection) {
      override def initEvent(input: GameState): CustomGameEvent =
        CustomGameEvent(input.currentTurn, input.currentCycle, name)
    }

  def playerEvent(name: String, player: GameState => Player, ruleBook: RuleBook): CustomEventInstance[GameState] =
    new AbstractCustomEventInstance[GameState](name, ruleBook.eventDefinitions.playerEventCollection) {
      override def initEvent(input: GameState): CustomGameEvent =
        CustomPlayerEvent(input.currentTurn, input.currentCycle, name, player(input))
    }

  def tileEvent(name: String, tile: GameState => Tile, ruleBook: RuleBook): CustomEventInstance[GameState] =
    new AbstractCustomEventInstance[GameState](name, ruleBook.eventDefinitions.tileEventCollection) {
      override def initEvent(input: GameState): CustomGameEvent =
        CustomTileEvent(input.currentTurn, input.currentCycle, name, tile(input))
    }
}