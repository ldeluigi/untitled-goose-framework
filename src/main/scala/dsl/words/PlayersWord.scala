package dsl.words

case class PlayersWord() {
  def start(on: OnWord): StartWord = new StartWord

}
