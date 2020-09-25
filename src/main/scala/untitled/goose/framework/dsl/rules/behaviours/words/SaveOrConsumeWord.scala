package untitled.goose.framework.dsl.rules.behaviours.words

sealed trait SaveOrConsumeWord

object SaveOrConsumeWord {

  /** Saves the filtered event to a history. */
  case class SaveWord() extends SaveOrConsumeWord {
    /** Can be followed by "consume" */
    def &&(consumeWord: ConsumeWord): SaveAndConsumeWord = SaveAndConsumeWord()
  }

  /** Consumes the filtered events from the buffer. */
  case class ConsumeWord() extends SaveOrConsumeWord {
    /** Can be followed by "save" */
    def &&(saveWord: SaveWord): SaveAndConsumeWord = SaveAndConsumeWord()
  }

  /** Saves and consumes the filtered events. */
  case class SaveAndConsumeWord() extends SaveOrConsumeWord

}