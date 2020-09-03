package dsl.words

class TurnWord() {

  def apply(playersWord: PlayersCanWord) = new TurnWord

  def are(unit: Unit): Unit = {} //TODO maybe define some sort of implicit that marks the block?

}
