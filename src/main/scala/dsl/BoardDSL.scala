package dsl

object BoardDSL {

  trait DefineKeyWord {
    def A(string: String): NameDefined = NameDefined()

    def has(number: Int): NumberDefined = NumberDefined()

    def in(disposition: Any): DispositionDefined = DispositionDefined()

    def -- : TileNameNumber = new TileNameNumber
  }

  private case class BoardDefinition() {
    def apply(name: String): NameDefined = NameDefined()
  }

  private case class NameDefined() {
    val board = new BoardKeyWord
  }

  private case class BoardKeyWord() {
    def apply(numberDefined: NumberDefined): BoardKeyWord = BoardKeyWord()

    val tiles = new TilesKeyword
  }

  private case class NumberDefined() {

  }

  private case class TilesKeyword() {
    def apply(dispositionDefined: DispositionDefined): TilesKeyword = TilesKeyword()

    def disposition(tile: TileNameNumber*): BoardCompleted = BoardCompleted()

  }

  private case class DispositionDefined() {


  }

  private case class TileNameNumber() {
    def apply(num: Int): TileNameNumber = ???

    def named(name: String): TileNameNumber = ???
  }

  private case class BoardCompleted() {

  }

  object test extends DefineKeyWord {
    A("GooseGame") board has(63) tiles in("Spiral") disposition(
      --(6) named "The Well",
      --(13) named "The Bridge",
      --(63) named "The End"
    )
  }

}


/*
A "goose game" board has 63 tiles in Spiral disposition {
  - 6 "the well",
  [...]
  -4,9, .. 54, 59 Group("goose")
  - 63 "The End"
 }
 */

//TODO tiles with no number??
