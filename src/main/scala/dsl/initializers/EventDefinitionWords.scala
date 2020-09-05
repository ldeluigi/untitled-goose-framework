package dsl.initializers

import dsl.events.DefineWord
import dsl.words.event.ValueWord

trait EventDefinitionWords {

  val Define: DefineWord = DefineWord()

  val value: ValueWord = ValueWord()
}
