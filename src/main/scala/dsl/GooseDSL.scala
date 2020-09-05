package dsl

import dsl.initializers._
import dsl.nodes.RuleBook
import dsl.words.ruleset.action.RulesWord
import model.TileIdentifier
import model.entities.runtime.{GameTemplate, GameTemplateBuilder}
import view.scalafx.View
import view.scalafx.board.GraphicDescriptor


trait GooseDSL extends App with Subjects with TilePropertyWords with BoardPropertyWords with RuleSetWords with EventDefinitionWords with Implicits {

  protected implicit val ruleBook: RuleBook = RuleBook()

  val Rules: RulesWord = new RulesWord()

  def All: CumulativeSubjects = CumulativeSubjects()

  def The: Subjects = this

  def the: Subjects = this

  override def main(args: Array[String]): Unit = {
    super.main(args)
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

