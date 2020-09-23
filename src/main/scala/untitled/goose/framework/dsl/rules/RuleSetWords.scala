package untitled.goose.framework.dsl.rules

import untitled.goose.framework.dsl.events.words.EventDefinitionWords
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.words.{EachTurnWord, WhenWord}
import untitled.goose.framework.dsl.rules.behaviours.words.BehaviourWords
import untitled.goose.framework.dsl.rules.cleanup.words.CleanupWords
import untitled.goose.framework.dsl.rules.players.words.{PlayerOrderProperty, PlayersWord}
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.EventsShortcuts
import untitled.goose.framework.model.entities.definitions.PlayerOrderingType.PlayerOrderingType

trait RuleSetWords extends EventDefinitionWords with EventsShortcuts with BehaviourWords with CleanupWords {

  val Players: PlayersWord = PlayersWord()

  def order(order: PlayerOrderingType): PlayerOrderProperty = PlayerOrderProperty(order)

  def Each: EachTurnWord = EachTurnWord()

  def always(implicit ruleBook: RuleBook): WhenWord = WhenWord(_ => true)

  def never(implicit ruleBook: RuleBook): WhenWord = WhenWord(_ => false)

  def when(condition: GameState => Boolean)(implicit ruleBook: RuleBook): WhenWord = WhenWord(condition)

  def When(condition: GameState => Boolean)(implicit ruleBook: RuleBook): WhenWord = WhenWord(condition)

}
