import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HelloWorldSpec extends AnyFlatSpec with Matchers {

  "Hello World" should "not be an empty String" in {
    val helloWorld = "Hello World"
    helloWorld should not be ""
  }
}