package untitled.goose.framework.dsl.rules.cleanup.words

import untitled.goose.framework.dsl.UtilityWords.EachWord

/** Enables "after [what]" */
case class AfterWord() {

  /** Enables "...after resolving each action..." */
  def resolving(eachWord: EachWord): AfterActionWord = AfterActionWord()
}
