package dsl

import dsl.words.GroupedTilesWord

trait CumulativeSubjects {

  val tiles: GroupedTilesWord = new GroupedTilesWord

}

object CumulativeSubjects {
  def apply(): CumulativeSubjects = new CumulativeSubjects {}
}
