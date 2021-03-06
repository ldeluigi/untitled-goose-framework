package untitled.goose.framework.model.entities.definitions

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

  "Snake disposition" should "place correctly a square" in {
    val sd = Disposition.snake(16)
    val list: List[(Int, Int)] = Range(0, 16).map(sd).toList
    list should be(List((0, 3), (1, 3), (2, 3), (3, 3), (3, 2), (2, 2), (1, 2), (0, 2), (0, 1), (1, 1), (2, 1), (3, 1), (3, 0), (2, 0), (1, 0), (0, 0)))
  }

  "Snake disposition" should "place correctly a square with odd sides" in {
    val sd = Disposition.snake(9)
    val list: List[(Int, Int)] = Range(0, 9).map(sd).toList
    list should be(List((0, 2), (1, 2), (2, 2), (2, 1), (1, 1), (0, 1), (0, 0), (1, 0), (2, 0)))
  }

  "Snake disposition" should "place correctly an incomplete square" in {
    val sd = Disposition.snake(15)
    val list: List[(Int, Int)] = Range(0, 15).map(sd).toList
    list should be(List((0, 3), (1, 3), (2, 3), (3, 3), (3, 2), (2, 2), (1, 2), (0, 2), (0, 1), (1, 1), (2, 1), (3, 1), (3, 0), (2, 0), (1, 0)))
  }

  "Snake disposition" should "place correctly an incomplete rectangle with ratio" in {
    val sd = Disposition.snake(15, 2)
    val list: List[(Int, Int)] = Range(0, 15).map(sd).toList
    list should be(List((0, 2), (1, 2), (2, 2), (3, 2), (4, 2), (5, 2), (5, 1), (4, 1), (3, 1), (2, 1), (1, 1), (0, 1), (0, 0), (1, 0), (2, 0)))
  }

  "Spiral disposition" should "have squared rows" in {
    val sd = Disposition.spiral(100)
    sd.rows should be(10)
  }

  "Spiral disposition" should "have squared columns" in {
    val sd = Disposition.spiral(100)
    sd.columns should be(10)
  }

  "Spiral disposition" should "place correctly a square" in {
    val sd = Disposition.spiral(16)
    val list: List[(Int, Int)] = Range(0, 16).map(sd).toList
    list should be(List[(Int, Int)]((0, 3), (1, 3), (2, 3), (3, 3), (3, 2), (3, 1), (3, 0), (2, 0), (1, 0), (0, 0), (0, 1), (0, 2), (1, 2), (2, 2), (2, 1), (1, 1)))
  }

  "Spiral disposition" should "place correctly a square with odd sides" in {
    val sd = Disposition.spiral(9)
    val list: List[(Int, Int)] = Range(0, 9).map(sd).toList
    list should be(List[(Int, Int)]((0, 2), (1, 2), (2, 2), (2, 1), (2, 0), (1, 0), (0, 0), (0, 1), (1, 1)))
  }

  "Spiral disposition" should "place correctly a square with odd sides, again" in {
    val sd = Disposition.spiral(25)
    val list: List[(Int, Int)] = Range(0, 25).map(sd).toList
    list should be(List[(Int, Int)]((0, 4), (1, 4), (2, 4), (3, 4), (4, 4), (4, 3), (4, 2), (4, 1), (4, 0), (3, 0),
      (2, 0), (1, 0), (0, 0), (0, 1), (0, 2), (0, 3), (1, 3), (2, 3), (3, 3), (3, 2), (3, 1), (2, 1), (1, 1), (1, 2), (2, 2)))
  }

  "Spiral disposition" should "place correctly an incomplete square" in {
    val sd = Disposition.spiral(15)
    val list: List[(Int, Int)] = Range(0, 15).map(sd).toList
    list should be(List[(Int, Int)]((0, 3), (1, 3), (2, 3), (3, 3), (3, 2), (3, 1), (3, 0), (2, 0), (1, 0), (0, 0), (0, 1), (0, 2), (1, 2), (2, 2), (2, 1)))
  }

  "Spiral disposition" should "place correctly an incomplete rectangle with ratio" in {
    val sd = Disposition.spiral(15, 2)
    val list: List[(Int, Int)] = Range(0, 15).map(sd).toList
    list should be(List((0, 2), (1, 2), (2, 2), (3, 2), (4, 2), (5, 2), (5, 1), (5, 0), (4, 0), (3, 0), (2, 0), (1, 0), (0, 0), (0, 1), (1, 1)))
  }

  "Ring disposition" should "have squared rows" in {
    val ld = Disposition.ring(100)
    ld.rows should be(26)
  }

  "Ring disposition" should "have squared columns" in {
    val ld = Disposition.ring(100)
    ld.columns should be(26)
  }

  "Ring disposition" should "place correctly a square" in {
    val ld = Disposition.ring(16)
    val list: List[(Int, Int)] = Range(0, 16).map(ld).toList
    list should be(List[(Int, Int)]((0, 4), (1, 4), (2, 4), (3, 4), (4, 4), (4, 3), (4, 2), (4, 1), (4, 0), (3, 0), (2, 0), (1, 0), (0, 0), (0, 1), (0, 2), (0, 3)))
  }

  "Ring disposition" should "place correctly an incomplete square" in {
    val ld = Disposition.ring(9)
    val list: List[(Int, Int)] = Range(0, 9).map(ld).toList
    list should be(List[(Int, Int)]((0, 2), (1, 2), (2, 2), (3, 2), (3, 1), (3, 0), (2, 0), (1, 0), (0, 0)))
  }

  "Ring disposition" should "place correctly an incomplete rectangle with ratio" in {
    val ld = Disposition.ring(15, 2)
    val list: List[(Int, Int)] = Range(0, 15).map(ld).toList
    list should be(List((0, 2), (1, 2), (2, 2), (3, 2), (4, 2), (5, 2), (6, 2), (6, 1), (6, 0), (5, 0), (4, 0), (3, 0), (2, 0), (1, 0), (0, 0)))
  }

  "Ring disposition" should "place correctly a complete rectangle with ratio" in {
    val ld = Disposition.ring(12, 2)
    val list: List[(Int, Int)] = Range(0, 12).map(ld).toList
    list should be(List((0, 1), (1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (5, 0), (4, 0), (3, 0), (2, 0), (1, 0), (0, 0)))
  }
}
