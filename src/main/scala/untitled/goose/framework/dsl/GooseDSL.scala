package untitled.goose.framework.dsl

import untitled.goose.framework.dsl.board.words.BoardWords
import untitled.goose.framework.dsl.dice.words.DiceWords
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.{RuleSetWords, RulesWord}
import untitled.goose.framework.model.GraphicDescriptor
import untitled.goose.framework.model.entities.definitions.{GameDefinition, GameDefinitionBuilder, TileIdentifier}
import untitled.goose.framework.view.scalafx.View

/**
 * The base class that needs to be extended when wanting to use the DSL in your project.
 * This provides the entry points for all the DSL syntaxes and also the methods to run the model checking and the
 * application with the generated game.
 */

trait GooseDSL extends BoardWords with RuleSetWords with Implicits with UtilityWords with DiceWords {

  /** Override is allowed as an extension feature. */
  protected implicit val ruleBook: RuleBook = RuleBook()

  /** Enables "Rules ..." */
  val Rules: RulesWord = new RulesWord()

  /** Enables "The ..." */
  def The: BoardWords = this

  /** Enables "the ..." */
  def the: BoardWords = this

  @deprecatedOverriding("You don't need to override this, override start method instead.")
  def main(args: Array[String]): Unit = {
    if (checkModel) {
      start(gameGeneration(), ruleBook.graphicMap.map)
    }
  }

  private def checkModel: Boolean = {
    val checkMessage = ruleBook.check
    checkMessage.zipWithIndex.map(e => e._2 + ": " + e._1).foreach(System.err.println)
    checkMessage.isEmpty
  }

  private def gameGeneration(): GameDefinition =
    GameDefinitionBuilder()
      .board(ruleBook.boardBuilder.complete())
      .playerOrderingType(ruleBook.ruleSet.playerOrderingType)
      .playersRange(ruleBook.ruleSet.playerRange)
      .actionRules(ruleBook.ruleSet.actionRules)
      .behaviourRules(ruleBook.ruleSet.behaviourRules)
      .cleanupRules(ruleBook.ruleSet.cleanupRules)
      .build()

  /**
   * Launch the application view with the given parameters generated from the DSL.
   * Override this method to change the view implementation used by the DSL.
   *
   * @param gameData   the collection of all game model related elements
   * @param graphicMap the map containing the GraphicDescriptors for the defined TileIdentifiers
   */
  protected def start(gameData: GameDefinition, graphicMap: Map[TileIdentifier, GraphicDescriptor]): Unit =
    new View(gameData, graphicMap).main(Array())


}

