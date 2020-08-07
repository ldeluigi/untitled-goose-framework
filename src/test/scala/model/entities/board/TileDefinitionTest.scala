package model.entities.board

import org.scalatest.flatspec.AnyFlatSpec

class TileDefinitionTest extends AnyFlatSpec {

  val num: Int = 1
  val name: String = "tile"
  val tileDefinitionOnlyWithNum: TileDefinition = TileDefinition(num)

  "A TileDefinition" should "have a number when specified" in {
    assert(tileDefinitionOnlyWithNum.number.get.equals(num))
  }

  it should "have a number and a name when specified" in {
    val tileDefinitionWithNumAndName: TileDefinition = TileDefinition(num, name)
    assert(tileDefinitionWithNumAndName.name.get.equals(name))
  }

  it should "have a number when specified but not have a name when created empty" in {
    assert(tileDefinitionOnlyWithNum.number.get.equals(num) && tileDefinitionOnlyWithNum.name.isEmpty)
  }

  it should "have a name when specified but not have a number when created empty" in {
    val tileDefinitionOnlyWithName: TileDefinition = TileDefinition(name)
    assert(tileDefinitionOnlyWithName.name.get.equals(name) && tileDefinitionOnlyWithName.number.isEmpty)
  }

}
