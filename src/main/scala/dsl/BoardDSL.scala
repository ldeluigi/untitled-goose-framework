package dsl

object BoardDSL {

  trait DefineKeyWord {
    //val board: BoardDefinition = BoardDefinition()
  }

  private case class BoardDefinition() {
    def named(name: String): NameDefined = NameDefined()
  }

  private case class NameDefined() {
    def has(totalTiles: Int): NumberDefined = NumberDefined()
  }

  private case class NumberDefined() {
    def tiles(text: String): TilesKeyword = TilesKeyword()
  }

  private case class TilesKeyword() {

    def in(disposition: Any): DispositionDefined = DispositionDefined()
  }

  private case class DispositionDefined() {

    def disposition(tile: TileNameNumber*): BoardCompleted = BoardCompleted()
  }

  private case class TileNameNumber() {

  }

  private case class BoardCompleted() {

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
