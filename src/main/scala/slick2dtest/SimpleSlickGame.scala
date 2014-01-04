package slick2dtest

import org.newdawn.slick._
import com.typesafe.scalalogging.slf4j.Logging

case class SimpleSlickGame(name: String) extends BasicGame(name) {
  def init(gc: GameContainer) {}

  def update(gc: GameContainer, i: Int) {}

  def render(gc: GameContainer, g: Graphics) {
    g.drawString("Howdy!", 100, 100)
  }
}

object SimpleSlickGame extends Logging with App {
  try {
    val appgc = new AppGameContainer(SimpleSlickGame("Simple Slick Game"))
    appgc.setDisplayMode(640, 480, false)
    appgc.start()
  } catch {
    case ex: SlickException => logger.error(s"Failed during startup: ${ex.getMessage}", ex)
  }
}
