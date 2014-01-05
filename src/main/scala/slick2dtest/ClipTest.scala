package slick2dtest

import org.newdawn.slick._
import com.typesafe.scalalogging.slf4j.Logging

object ClipTest extends BasicGame("Clip Test") with Logging {

  // The current angle of rotation
  private var ang = 0.0f

  // True if we're showing world clipping
  private var world = false

  // True if we're showing screen clipping
  private var clip = false

  def main(args: Array[String]) {
    try {
      val container = new AppGameContainer(ClipTest)
      container.setDisplayMode(800, 600, false)
      container.start()
    } catch {
      case ex: SlickException => logger.error(s"Failed during startup: ${ex.getMessage}", ex)
    }
  }

  def init(container: GameContainer) {
  }

  def update(container: GameContainer, delta: Int) = ang += delta * 0.01f

  def render(container: GameContainer, g: Graphics) {
    g.setColor(Color.white)
    g.drawString("1 - No Clipping", 100, 10)
    g.drawString("2 - Screen Clipping", 100, 30)
    g.drawString("3 - World Clipping", 100, 50)

    if (world) g.drawString("WORLD CLIPPING ENABLED", 200, 80)
    if (clip) g.drawString("SCREEN CLIPPING ENABLED", 200, 80)

    g.rotate(400, 400, ang)

    if (world) g.setWorldClip(350, 302, 100, 196)
    if (clip) g.setClip(350, 302, 100, 196)

    g.setColor(Color.red)
    g.fillOval(300, 300, 200, 200)
    g.setColor(Color.blue)
    g.fillRect(390, 200, 20, 400)
    g.clearClip()
    g.clearWorldClip()
  }

  override def keyPressed(key: Int, c: Char) {
    key match {
      case Input.KEY_1 =>
        world = false
        clip = false
      case Input.KEY_2 =>
        world = false
        clip = true
      case Input.KEY_3 =>
        world = true
        clip = false
    }
  }
}
