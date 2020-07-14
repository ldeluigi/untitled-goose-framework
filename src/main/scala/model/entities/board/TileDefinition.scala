package model.entities.board

trait TileDefinition {
  def number: Option[Int]

  def name: Option[String]

  def tileType: Option[List[String]]

  def next: Option[TileDefinition]
}


object TileDefinition {
  def apply(number: Int) = TileDefinitionImpl(number) //TODO change this
}

case class TileDefinitionImpl(num: Int) extends TileDefinition {

  override def number: Option[Int] = Some(num)

  override def name: Option[String] = None

  override def tileType: Option[List[String]] = None

  override def next: Option[TileDefinition] = None

  override def equals(obj: TileDefinition): Boolean = {
    if(number.isDefined && obj.number.isDefined){
      return number.get == obj.number.get
    }

    if(name.isDefined && obj.name.isDefined){
      return name.get == obj.name.get
    }
  }
}