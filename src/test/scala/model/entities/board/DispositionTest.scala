package model.entities.board

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.List

class DispositionTest extends AnyFlatSpec with Matchers {

  "Snake disposition" should "have squared rows" in {
    val sd = Disposition.snake(100)
    sd.rows should be(10)
  }

  "Snake disposition" should "have squared columns" in {
    val sd = Disposition.snake(100)
    sd.columns should be(10)
  }

    "Snake disposition" should "place correctly" in {
      val sd = Disposition.snake(16)
      val list: List[(Int, Int)] = Range(0, 16).map(sd.tilePlacement).toList
      list should be(List((0,3), (1,3), (2,3), (3,3), (3,2), (2,2), (1,2), (0,2), (0,1), (1,1), (2,1), (3,1), (3,0), (2,0), (1,0), (0,0)))
    }

  "Spiral disposition" should "have squared rows" in {
    val sd = Disposition.spiral(100)
    sd.rows should be(10)
  }

  "Spiral disposition" should "have squared columns" in {
    val sd = Disposition.spiral(100)
    sd.columns should be(10)
  }

  // TODO fix test
  //  "Spiral disposition" should "place correctly" in {
  //    val sd = Disposition.spiral(16)
  //    val list: List[(Int, Int)] = Range(0, 16).map(sd.tilePlacement).toList
  //    list should be(List[(Int, Int)]((3, 0), (3, 1), (3, 2), (3, 3), (2, 3), (1, 3), (0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (2, 0), (2, 1), (2, 2), (1, 2), (1, 1)))
  //  }
}
