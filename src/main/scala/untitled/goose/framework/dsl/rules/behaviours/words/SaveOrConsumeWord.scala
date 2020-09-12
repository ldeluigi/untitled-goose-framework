package untitled.goose.framework.dsl.rules.behaviours.words

sealed trait SaveOrConsumeWord

object SaveOrConsumeWord {

  case class SaveWord() extends SaveOrConsumeWord {
    def &&(consumeWord: ConsumeWord): SaveAndConsumeWord = SaveAndConsumeWord()
  }

  case class ConsumeWord() extends SaveOrConsumeWord {
    def &&(saveWord: SaveWord): SaveAndConsumeWord = SaveAndConsumeWord()
  }

  case class SaveAndConsumeWord() extends SaveOrConsumeWord

}