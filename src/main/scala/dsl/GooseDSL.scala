package dsl

import dsl.nodes.RuleBook
import dsl.words.{BoardPropertyWords, RulesWord, TilePropertyWords}
import model.entities.board.{Board, Position}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}
import model.{GameData, TileIdentifier}
import view.View
import view.board.GraphicDescriptor


trait GooseDSL extends App with Subjects with TilePropertyWords with BoardPropertyWords {

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

  private def gameGeneration(): GameData =
    new GameData {
      override def board: Board = ruleBook.boardBuilder.complete()

      override def ruleSet: RuleSet = PriorityRuleSet(
        tiles => Position(tiles.toList.sorted.take(1).head),
        PlayerOrdering.orderedRandom,
        1 to 10,
        Set(),
        Seq(),
        Seq(),
      )
    }

  private def start(gameData: GameData, graphicMap: Map[TileIdentifier, GraphicDescriptor]): Unit =
    new View(gameData, graphicMap).main(Array())


}

