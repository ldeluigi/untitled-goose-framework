package untitled.goose.framework.dsl.events.words

trait EventDefinitionWords {

  val Define: DefineWord = DefineWord()

  def customEvent(name: String): CustomEventInstance = CustomEventInstance(name)
}
