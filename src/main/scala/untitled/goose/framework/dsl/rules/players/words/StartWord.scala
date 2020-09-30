package untitled.goose.framework.dsl.rules.players.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.definitions.TileIdentifier

/** Used for "start [where]" */
case class StartWord() {

  /** Enables "... tile [number]" */
  def tile(num: Int)(implicit ruleBook: RuleBook): Unit = ruleBook.boardBuilder.withFirstTile(TileIdentifier(num))

  /** Enables "... tile [name]" */
  def tile(name: String)(implicit ruleBook: RuleBook): Unit = ruleBook.boardBuilder.withFirstTile(TileIdentifier(name))

}
