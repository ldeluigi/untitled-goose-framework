package dsl.words

import dsl.board.properties.{DispositionProperty, DispositionType}

trait BoardPropertyWords {

  val board: BoardWord = BoardWord()

  def disposition(disType: DispositionType): DispositionProperty = ???

}
