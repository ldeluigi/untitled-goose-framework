package model.rules

trait PlayerCardinalityRule {

  def minimumPlayers: Int = 2

  def maximumNumberPlayers(): Unit = Option[Int]

}

object PlayerCardinalityRule {

  private class PlayerCardinalityRuleImpl(minimum: Int) extends PlayerCardinalityRule {

    override def minimumPlayers: Int = minimum
  }

  def apply(minimumPlayers: Int): PlayerCardinalityRule = new PlayerCardinalityRuleImpl(minimumPlayers)
}

