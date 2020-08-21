package model.rules

trait PlayerCardinalityRule {

  var fixedMinimum: Int = 2

  def minimumPlayers: Unit

  def maximumPlayers: Option[Int]

  //def rangeOfPlayers: Range

}

object PlayerCardinalityRule {

  private class PlayerCardinalityRuleImpl(definedMin: Option[Int], definedMax: Option[Int]) extends PlayerCardinalityRule {

    override def minimumPlayers: Unit = {
      if (definedMin.isDefined) {
        fixedMinimum = definedMin.get
      }
    }

    override def maximumPlayers: Option[Int] = definedMax

    //override def rangeOfPlayers: Range = fixedMinimum to maximumPlayers.getOrElse()
  }

  def apply(min: Int, max: Int): PlayerCardinalityRule = new PlayerCardinalityRuleImpl(Some(min), Some(max))

  //def apply(range: Range): PlayerCardinalityRule = new PlayerCardinalityRuleImpl(range)
}

