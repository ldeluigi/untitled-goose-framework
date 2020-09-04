package dsl.words

import dsl.nodes.RuleBook
import dsl.properties.PlayerOrderProperty
import model.PlayerOrderingType.PlayerOrderingType
import model.entities.runtime.GameState

trait RuleSetWords {

  val Players: PlayersWord = PlayersWord()

  def on: OnWord = OnWord()

  def order(order: PlayerOrderingType): PlayerOrderProperty = PlayerOrderProperty(order)

  def Each: EachWord = EachWord()

  val players: PlayersAreWord = PlayersAreWord()

  def always(implicit ruleBook: RuleBook): WhenWord = WhenWord(_ => true)

  def never(implicit ruleBook: RuleBook): WhenWord = WhenWord(_ => false)

  def when(condition: GameState => Boolean)(implicit ruleBook: RuleBook): WhenWord = WhenWord(condition)

  def to: ToWord = ToWord()
}
