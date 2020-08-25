package dsl

import dsl.words.RulesWord


trait GooseDSL extends App with HasPropertyBuilder with Implicits {

  protected implicit val ruleBook: RuleBook = new RuleBook()

  val Rules: RulesWord = new RulesWord()

  def The: Subjects.type = Subjects

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
