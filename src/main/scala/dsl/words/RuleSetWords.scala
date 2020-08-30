package dsl.words

import dsl.properties.PlayerOrderProperty
import model.PlayerOrderingType.PlayerOrderingType

trait RuleSetWords {

  val Players: PlayersWord = PlayersWord()

  def on: OnWord = OnWord()

  def order(order: PlayerOrderingType): PlayerOrderProperty = PlayerOrderProperty(order)
}
