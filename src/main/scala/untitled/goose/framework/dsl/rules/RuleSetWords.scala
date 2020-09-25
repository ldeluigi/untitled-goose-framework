package untitled.goose.framework.dsl.rules

import untitled.goose.framework.dsl.events.words.EventDefinitionWords
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.words.{EachTurnWord, WhenWord}
import untitled.goose.framework.dsl.rules.behaviours.words.BehaviourWords
import untitled.goose.framework.dsl.rules.cleanup.words.CleanupWords
import untitled.goose.framework.dsl.rules.players.words.{PlayerOrderProperty, PlayersWord}
import untitled.goose.framework.model.entities.definitions.PlayerOrderingType.PlayerOrderingType
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.EventsShortcuts

trait RuleSetWords extends EventDefinitionWords with EventsShortcuts with BehaviourWords with CleanupWords {

  /** Enables "Players ..." */
  val Players: PlayersWord = PlayersWord()

  /** Enables "... order([orderType])" */
  def order(order: PlayerOrderingType): PlayerOrderProperty = PlayerOrderProperty(order)

  /** Enables "Each turn ..." */
  def Each: EachTurnWord = EachTurnWord()

  /** Enables "... always [verb] ..." */
  def always(implicit ruleBook: RuleBook): WhenWord = WhenWord(_ => true)

  /** Enables "... never [verb] ..." */
  def never(implicit ruleBook: RuleBook): WhenWord = WhenWord(_ => false)

  /** Enables "... when([condition]) [verb] ..." */
  def when(condition: GameState => Boolean)(implicit ruleBook: RuleBook): WhenWord = WhenWord(condition)

  /** Enables "When(condition) [verb] ..." */
  def When(condition: GameState => Boolean)(implicit ruleBook: RuleBook): WhenWord = WhenWord(condition)

}
