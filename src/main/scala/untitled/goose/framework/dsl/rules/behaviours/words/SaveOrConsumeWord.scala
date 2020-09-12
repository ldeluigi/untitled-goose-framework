package untitled.goose.framework.dsl.rules.behaviours.words

sealed trait SaveOrConsumeWord

object SaveOrConsumeWord {

  case class SaveWord() extends SaveOrConsumeWord

  case class ConsumeWord() extends SaveOrConsumeWord

}