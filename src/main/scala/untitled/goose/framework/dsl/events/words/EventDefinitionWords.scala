package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.{GameState, Player, Tile}

/** Words for custom event definition. */
trait EventDefinitionWords {

  /** Enables "Define [thing] ..." */
  val Define: DefineWord = DefineWord()

  /** Enables "customGameEvent ([name]) [properties and values]" */
  def customGameEvent(name: String)(implicit ruleBook: RuleBook): CustomEventInstance[GameState] =
    CustomEventInstances.gameEvent(name, ruleBook)

  /** Enables "customPlayerEvent ([name], state => [player]) [properties and values]" */
  def customPlayerEvent(name: String, player: GameState => Player)(implicit ruleBook: RuleBook): CustomEventInstance[GameState] =
    CustomEventInstances.playerEvent(name, player, ruleBook)

  /** Enables "customTileEvent ([name], state => [tile]) [properties and values]" */
  def customTileEvent(name: String, tile: GameState => Tile)(implicit ruleBook: RuleBook): CustomEventInstance[GameState] =
    CustomEventInstances.tileEvent(name, tile, ruleBook)
}
