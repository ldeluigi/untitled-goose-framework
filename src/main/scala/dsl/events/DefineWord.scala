package dsl.events

case class DefineWord() {

  def event(name: String): EventPropertiesWord = ???

  def playerEvent(name: String): EventPropertiesWord = ???

  def tileEvent(name: String): EventPropertiesWord = ???
}
