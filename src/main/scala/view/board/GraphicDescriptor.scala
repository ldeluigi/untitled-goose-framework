package view.board

import model.TileIdentifier
import scalafx.scene.shape.Shape

//conversione impliccita e mappa nella view con graphic descriptor dentro cui metto la conversione implicita che usa direttamente sclafx
//capire come fare le conversioni implicite da num a graphic.. da nome a graphic..
//e dal gruppo devo creare un oggetto gruppo che prende una scritta e crea un oggetto con un solo campo,
//mi serve perchè così ho un tipo su cui fare la convesione implicita -> classe che prende una stronga e ha un campo stringa da creare sopra come classe dove faccio le conversioni implicite
/* Map(
   "oca" -> GraphicDescriptor("oca.png"), //prende una risorsa come path..
    15 -> GraphicDescriptor("pozzo.png"),
    Group("verde") -> GraphicDescriptor(Color.green)
    )
)*/

//poi guardare nella view come gestire queste cose, leggendo la mappa e convertirle in aspetti grafici con le priorità seguenti -> nome > gruppo > num

trait GraphicDescriptor {
  //c'è una mappa da tileidentifier a graphicdescriptor ->
  // implicit conversion che dice che una tupla numero group qualcosa diventa graphicdescriptor
  // creo tante conversioni implicite da tupla sopra a sotto...

  implicit def map(tileIdentifier: TileIdentifier): GraphicDescriptor

  def color: Shape

}

object GraphicDescriptor {

  //creare la classe gruppo

  private class GraphicDescriptorImpl extends GraphicDescriptor {

    override implicit def map(tileIdentifier: TileIdentifier): GraphicDescriptor = {
      ???

    }

    override def color: Shape = ???
  }

}
