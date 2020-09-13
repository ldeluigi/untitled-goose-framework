package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.words.custom.ActionCustomEventInstance
import untitled.goose.framework.model.entities.runtime.{GameState, Player, Tile}

trait EventDefinitionWords {

  val Define: DefineWord = DefineWord()

  def customGameEvent(name: String)(implicit ruleBook: RuleBook): ActionCustomEventInstance =
    ActionCustomEventInstance.gameEvent(name, ruleBook)

  def customPlayerEvent(name: String, player: GameState => Player)(implicit ruleBook: RuleBook): ActionCustomEventInstance =
    ActionCustomEventInstance.playerEvent(name, player, ruleBook)

  def customTileEvent(name: String, tile: GameState => Tile)(implicit ruleBook: RuleBook): ActionCustomEventInstance =
    ActionCustomEventInstance.tileEvent(name, tile, ruleBook)
}
