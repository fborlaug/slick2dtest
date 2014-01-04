package slick2dtest

import org.newdawn.slick._
import com.typesafe.scalalogging.slf4j.Logging

case class ImagesTest(name: String) extends BasicGame(name) {
  var img : Image = null

  def init(gc: GameContainer) {
    img = new Image("dices.png")
  }

  def update(gc: GameContainer, i: Int) {}

  def render(gc: GameContainer, g: Graphics) {
    img.draw(50, 50)
  }
}

object ImagesTest extends Logging with App {

  try {
    val appgc = new AppGameContainer(ImagesTest("Image Test"))
    appgc.setDisplayMode(640, 480, false)
    appgc.start()
  } catch {
    case ex: SlickException => logger.error(s"Failed during startup: ${ex.getMessage}", ex)
  }
}