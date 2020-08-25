package model.rules

trait PlayerCardinalityRule {

  var fixedMinimum: Int = 2

  /** Sets the minimum game's players. */
  def minimumPlayers(): Unit

  /** Sets the minimum game's players, if specified. */
  def maximumPlayers: Option[Int]

}

object PlayerCardinalityRule {

  private class PlayerCardinalityRuleImpl(definedMin: Option[Int], definedMax: Option[Int]) extends PlayerCardinalityRule {

    override def minimumPlayers(): Unit = {
      if (definedMin.isDefined) {
        fixedMinimum = definedMin.get
      }
    }

    override def maximumPlayers: Option[Int] = definedMax
  }

  /** A factory for a new PlayerCardinality rule.
   *
   * @param min minimum number of players
   * @param max maximum number of players
   * @return the newly created PlayerCardinality rule.
   */
  def apply(min: Int, max: Int): PlayerCardinalityRule = new PlayerCardinalityRuleImpl(Some(min), Some(max))
}

