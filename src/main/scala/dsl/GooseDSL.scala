package dsl

import dsl.nodes.RuleBook
import dsl.words.{BoardPropertyWords, RulesWord, TilePropertyWords}


trait GooseDSL extends App with Subjects with TilePropertyWords with BoardPropertyWords {

  protected implicit val ruleBook: RuleBook = RuleBook()

  val Rules: RulesWord = new RulesWord()

  def All: CumulativeSubjects = ???

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

