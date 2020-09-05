package dsl

import dsl.board.words.BoardWords
import dsl.nodes.RuleBook
import dsl.rules.RuleSetWords
import dsl.rules.actions.words.RulesWord
import model.TileIdentifier
import model.entities.runtime.{GameTemplate, GameTemplateBuilder}
import view.scalafx.View
import view.scalafx.board.GraphicDescriptor


trait GooseDSL extends BoardWords with RuleSetWords with Implicits with UtilityWords {

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

