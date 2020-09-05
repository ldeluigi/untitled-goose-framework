package dsl.initializers

import dsl.words.tile.GroupedTilesWord

trait CumulativeSubjects {

  val tiles: GroupedTilesWord = new GroupedTilesWord

}

object CumulativeSubjects {
  def apply(): CumulativeSubjects = new CumulativeSubjects {}
}
