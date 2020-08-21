package model.rules

trait PlayerCardinalityRule {

  var fixedMinimum: Int = 2

  def minimumPlayers: Unit

  def maximumPlayers: Option[Int]

}

object PlayerCardinalityRule {

  private class PlayerCardinalityRuleImpl(definedMin: Option[Int], definedMax: Option[Int]) extends PlayerCardinalityRule {

    override def minimumPlayers: Unit = {
      if (definedMin.isDefined) {
        fixedMinimum = definedMin.get
      }
    }

    override def maximumPlayers: Option[Int] = definedMax

  }

  def apply(max: Int): PlayerCardinalityRule = new PlayerCardinalityRuleImpl(None, Some(max))

  def apply(min: Int, max: Int): PlayerCardinalityRule = new PlayerCardinalityRuleImpl(Some(min), Some(max))
}

