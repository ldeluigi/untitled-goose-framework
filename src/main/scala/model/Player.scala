package model

trait Player {
  def name: String //ID???

  def hystory: List[GameEventHandler]
}

object Player{
  def apply(name: String) = MockPlayer(name)
}

case class MockPLayer(name: String) extends Player{
  override def name: String = name

  override def hystory: List[Any] = ???
}

object Player {
  def apply(): Player = PlayerImpl()
}

case class PlayerImpl() extends Player {

}
