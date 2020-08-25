package dsl.words

import dsl.board.properties.DispositionProperty

trait DispositionTypeWord {
  def disposition: DispositionProperty
}

object DispositionTypeWord {

  val Spiral: DispositionTypeWord = new DispositionTypeWord {
    override def disposition: DispositionProperty = ???
  }

  val Loop: DispositionTypeWord = new DispositionTypeWord {
    override def disposition: DispositionProperty = ???
  }

  val Snake: DispositionTypeWord = new DispositionTypeWord {
    override def disposition: DispositionProperty = ???
  }


}

