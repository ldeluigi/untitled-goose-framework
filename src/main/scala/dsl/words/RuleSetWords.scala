package dsl.words

import dsl.properties.PlayerOrderProperty
import model.PlayerOrderingType.PlayerOrderingType
import model.game.GameState

trait RuleSetWords {

  val Players: PlayersWord = PlayersWord()

  def on: OnWord = OnWord()

  def order(order: PlayerOrderingType): PlayerOrderProperty = PlayerOrderProperty(order)

  def Each: EachWord = EachWord()

  val players: PlayersCanWord = PlayersCanWord()

  def always: WhenWord = WhenWord(_ => true)

  def when(condition: GameState => Boolean): WhenWord = WhenWord(condition)

  def to: ToWord = ToWord()
}
