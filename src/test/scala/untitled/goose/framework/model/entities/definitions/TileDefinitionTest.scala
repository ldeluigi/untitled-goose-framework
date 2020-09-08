package untitled.goose.framework.model.entities.definitions

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TileDefinitionTest extends AnyFlatSpec with Matchers {

  val num: Int = 1
  val name: String = "tile"
  val tileDefinitionOnlyWithNum: TileDefinition = TileDefinition(num)

  "A TileDefinition" should "have a number when specified" in {
    tileDefinitionOnlyWithNum.number.get should equal(num)
  }

  it should "have a number and a name when specified" in {
    val tileDefinitionWithNumAndName: TileDefinition = TileDefinition(num, name)
    tileDefinitionWithNumAndName.name.get should equal(name)
  }

  it should "have a number when specified but not have a name when created empty" in {
    tileDefinitionOnlyWithNum.number.get should equal(num)
    tileDefinitionOnlyWithNum.name.isEmpty should be(true)
  }

  it should "have a name when specified but not have a number when created empty" in {
    val tileDefinitionOnlyWithName: TileDefinition = TileDefinition(name)
    tileDefinitionOnlyWithName.name.get should equal(name)
    tileDefinitionOnlyWithName.number.isEmpty should be(true)
  }

}
