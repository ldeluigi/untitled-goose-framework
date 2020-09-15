package untitled.goose.framework.dsl.rules.actions.words.custom

import untitled.goose.framework.dsl.events.nodes.EventDefinitionCollection
import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.events.words.CustomEventInstance.AbstractCustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.{GameState, Player, Tile}
import untitled.goose.framework.model.events.{CustomGameEvent, CustomPlayerEvent, CustomTileEvent}

trait ActionCustomEventInstance extends CustomEventInstance[GameState]

object ActionCustomEventInstance {

  private abstract class AbstractActionCustomEventInstance(name: String,
                                                           definedEvents: EventDefinitionCollection)
    extends AbstractCustomEventInstance[GameState](name, definedEvents) with ActionCustomEventInstance

  def gameEvent(name: String, ruleBook: RuleBook): ActionCustomEventInstance =
    new AbstractActionCustomEventInstance(name, ruleBook.eventDefinitions.gameEventCollection) {
      override def initEvent(input: GameState): CustomGameEvent =
        CustomGameEvent(input.currentTurn, input.currentCycle, name)
    }

  def playerEvent(name: String, player: GameState => Player, ruleBook: RuleBook): ActionCustomEventInstance =
    new AbstractActionCustomEventInstance(name, ruleBook.eventDefinitions.playerEventCollection) {
      override def initEvent(input: GameState): CustomGameEvent =
        CustomPlayerEvent(input.currentTurn, input.currentCycle, name, player(input))
    }

  def tileEvent(name: String, tile: GameState => Tile, ruleBook: RuleBook): ActionCustomEventInstance =
    new AbstractActionCustomEventInstance(name, ruleBook.eventDefinitions.tileEventCollection) {
      override def initEvent(input: GameState): CustomGameEvent =
        CustomTileEvent(input.currentTurn, input.currentCycle, name, tile(input))
    }
}