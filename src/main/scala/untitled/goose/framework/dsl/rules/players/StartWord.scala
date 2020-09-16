package untitled.goose.framework.dsl.rules.players

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.definitions.TileIdentifier

case class StartWord() {
  def tile(num: Int)(implicit ruleBook: RuleBook): Unit = ruleBook.boardBuilder.withFirstTile(TileIdentifier(num))

  def tile(name: String)(implicit ruleBook: RuleBook): Unit = ruleBook.boardBuilder.withFirstTile(TileIdentifier(name))

}
