package view.board

import scalafx.scene.paint.Color

//conversione implicita e mappa nella view con graphic descriptor dentro cui metto la conversione implicita che usa direttamente sclafx
//capire come fare le conversioni implicite da num a graphic.. da nome a graphic..
//e dal gruppo devo creare un oggetto gruppo che prende una stringa e crea un oggetto con un solo campo,
//mi serve perchè così ho un tipo su cui fare la convesione implicita -> classe che prende una stringa e ha un campo stringa da creare sopra come classe dove faccio le conversioni implicite
/* Map(
   "oca" -> GraphicDescriptor("oca.png"), //prende una risorsa come path..
    15 -> GraphicDescriptor("pozzo.png"),
    Group("verde") -> GraphicDescriptor(Color.green)
    )
)*/

//poi guardare nella view come gestire queste cose, leggendo la mappa e convertirle in aspetti grafici con le priorità seguenti -> nome > gruppo > num


/*classe contenente una descrizione grafica*/
trait GraphicDescriptor {

  // implicit conversion che dice che una tupla numero group qualcosa diventa graphicdescriptor
  // creo tante conversioni implicite da tupla sopra a sotto...

  def color: Option[Color]

  def path: Option[String]
}

object GraphicDescriptor {

  private class GraphicDescriptorImpl(val color: Option[Color], val path: Option[String]) extends GraphicDescriptor {
  }

  def apply(specifiedColor: Color): GraphicDescriptor = new GraphicDescriptorImpl(Some(specifiedColor), None)

  def apply(path: String): GraphicDescriptor = new GraphicDescriptorImpl(None, Some(path))

  def apply(specifiedColor: Color, path: String): GraphicDescriptor = new GraphicDescriptorImpl(Some(specifiedColor), Some(path))

}
