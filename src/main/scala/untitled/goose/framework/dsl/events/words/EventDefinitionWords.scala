package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.{GameState, Player, Tile}

trait EventDefinitionWords {

  val Define: DefineWord = DefineWord()

  def customGameEvent(name: String)(implicit ruleBook: RuleBook): CustomEventInstance[GameState] =
    CustomEventInstances.gameEvent(name, ruleBook)

  def customPlayerEvent(name: String, player: GameState => Player)(implicit ruleBook: RuleBook): CustomEventInstance[GameState] =
    CustomEventInstances.playerEvent(name, player, ruleBook)

  def customTileEvent(name: String, tile: GameState => Tile)(implicit ruleBook: RuleBook): CustomEventInstance[GameState] =
    CustomEventInstances.tileEvent(name, tile, ruleBook)
}
