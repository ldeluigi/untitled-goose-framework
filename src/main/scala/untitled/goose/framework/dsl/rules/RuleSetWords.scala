package untitled.goose.framework.dsl.rules

import untitled.goose.framework.dsl.events.words.EventDefinitionWords
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.words.{EachWord, PlayersAreWord, ToWord, WhenWord}
import untitled.goose.framework.dsl.rules.players.{PlayerOrderProperty, PlayersWord}
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.rules.ruleset.PlayerOrderingType.PlayerOrderingType

//TODO Perchè sta cosa??? Non ne capisco il senso @Deloo
trait RuleSetWords extends EventDefinitionWords {

  val Players: PlayersWord = PlayersWord()

  def order(order: PlayerOrderingType): PlayerOrderProperty = PlayerOrderProperty(order)

  def Each: EachWord = EachWord()

  val players: PlayersAreWord = PlayersAreWord()

  def always(implicit ruleBook: RuleBook): WhenWord = WhenWord(_ => true)

  def never(implicit ruleBook: RuleBook): WhenWord = WhenWord(_ => false)

  def when(condition: GameState => Boolean)(implicit ruleBook: RuleBook): WhenWord = WhenWord(condition)

  def to: ToWord = ToWord()

}
