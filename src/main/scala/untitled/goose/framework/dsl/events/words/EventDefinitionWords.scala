package untitled.goose.framework.dsl.events.words

trait EventDefinitionWords {

  val Define: DefineWord = DefineWord()

  val value: ValueWord = ValueWord()

  def customEvent(name: String): CustomEventInstance = CustomEventInstance(name)
}
