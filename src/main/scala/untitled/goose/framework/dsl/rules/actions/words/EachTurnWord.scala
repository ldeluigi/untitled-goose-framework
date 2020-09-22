package untitled.goose.framework.dsl.rules.actions.words

/** Used for "each turn" word in actions. */
case class EachTurnWord() {

  /** Enables "each turn [who] ..." */
  def turn: TurnWord = new TurnWord

}
