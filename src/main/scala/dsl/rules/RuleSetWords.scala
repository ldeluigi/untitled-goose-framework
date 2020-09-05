package dsl.rules

import dsl.events.words.EventDefinitionWords
import dsl.nodes.RuleBook
import dsl.rules.actions.words.{EachWord, PlayersAreWord, ToWord, WhenWord}
import dsl.rules.players.{PlayerOrderProperty, PlayersWord}
import model.PlayerOrderingType.PlayerOrderingType
import model.entities.runtime.GameState

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
