package engine.events.root

trait GameEvent extends Serializable {

  def name: String

  def group: List[String]

}
