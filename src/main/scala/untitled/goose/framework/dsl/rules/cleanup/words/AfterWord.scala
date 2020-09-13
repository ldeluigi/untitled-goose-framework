package untitled.goose.framework.dsl.rules.cleanup.words

import untitled.goose.framework.dsl.UtilityWords.EachWord

case class AfterWord() {

  def resolving(eachWord: EachWord): AfterActionWord = AfterActionWord()
}
