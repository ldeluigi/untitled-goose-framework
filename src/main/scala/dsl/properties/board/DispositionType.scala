package dsl.properties.board

case class DispositionType(name: String) {

}

object DispositionType {

  val Spiral: DispositionType = new DispositionType("Spiral")

  val Loop: DispositionType = new DispositionType("Loop")

  val Snake: DispositionType = new DispositionType("Snake")

}
