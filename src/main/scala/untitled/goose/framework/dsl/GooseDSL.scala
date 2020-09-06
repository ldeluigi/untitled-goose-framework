package untitled.goose.framework.dsl

import untitled.goose.framework.dsl.board.words.BoardWords
import untitled.goose.framework.dsl.dice.words.DiceWords
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.RuleSetWords
import untitled.goose.framework.dsl.rules.actions.words.RulesWord
import untitled.goose.framework.model.TileIdentifier
import untitled.goose.framework.model.entities.runtime.{GameTemplate, GameTemplateBuilder}
import untitled.goose.framework.view.scalafx.View
import untitled.goose.framework.view.scalafx.board.GraphicDescriptor


trait GooseDSL extends BoardWords with RuleSetWords with Implicits with UtilityWords with DiceWords {

  protected implicit val ruleBook: RuleBook = RuleBook()

  val Rules: RulesWord = new RulesWord()

  def The: BoardWords = this

  def the: BoardWords = this

  def main(args: Array[String]): Unit = {
    if (checkModel) {
      start(gameGeneration(), ruleBook.graphicMap.map)
    }
  }

  private def checkModel: Boolean = {
    val checkMessage = ruleBook.check
    checkMessage.foreach(System.err.println)
    checkMessage.isEmpty
  }

  private def gameGeneration(): GameTemplate = {
    GameTemplateBuilder()
      .board(ruleBook.boardBuilder.complete())
      .startPositionStrategy(ruleBook.ruleSet.getFirstTileSelector)
      .playerOrderingType(ruleBook.ruleSet.playerOrderingType)
      .playersRange(ruleBook.ruleSet.playerRange)
      .actionRules(ruleBook.ruleSet.actionRules)
      .behaviourRules(ruleBook.ruleSet.behaviourRules)
      .cleanupRules(ruleBook.ruleSet.cleanupRules)
      .build
  }

  private def start(gameData: GameTemplate, graphicMap: Map[TileIdentifier, GraphicDescriptor]): Unit =
    new View(gameData, graphicMap).main(Array())


}

