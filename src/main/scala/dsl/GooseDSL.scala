package dsl

import dsl.board.properties.BoardHasProperty
import dsl.words.{RulesWord, TilePropertyWords}


trait GooseDSL extends App with Implicits with Subjects with BoardHasProperty with TilePropertyWords {

  protected implicit val ruleBook: RuleBook = new RuleBook()

  val Rules: RulesWord = new RulesWord()

  def The: Subjects = this

  def the: Subjects = this

  override def main(args: Array[String]): Unit = {
    super.main(args)
    if (checkModel) {
      start()
    }
  }

  private def checkModel: Boolean = {
    ruleBook.check
  }

  private def start(): Unit = println(ruleBook)

}

