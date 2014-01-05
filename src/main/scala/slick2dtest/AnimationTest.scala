package slick2dtest

import org.newdawn.slick._
import com.typesafe.scalalogging.slf4j.Logging

object AnimationTest extends BasicGame("Animation Test") with Logging {

  private var animation: Animation = null
  private var limited: Animation = null
  private var manual: Animation = null
  private var pingPong: Animation = null
  private var start: Int = 5000
  private var sheet: SpriteSheet = null

  def init(gc: GameContainer) {
    sheet = new SpriteSheet("homeranim.png", 36, 65)
    animation = initAnimation()
    limited = initAnimation()
    limited stopAt 7
    manual = initAnimation(autoUpdate = false)
    pingPong = new Animation(sheet, 0, 0, 7, 0, true, 150, true)
    pingPong setPingPong true
  }

  def initAnimation(autoUpdate: Boolean = true) = {
    val animation = new Animation(autoUpdate)
    (0 until 8).map(i => animation.addFrame(sheet.getSprite(i, 0), 150))
    animation
  }

  def update(gc: GameContainer, delta: Int) {
    if (gc.getInput.isKeyDown(Input.KEY_1)) manual.update(delta)
    if (start >= 0) start -= delta
  }

  def render(gc: GameContainer, g: Graphics) {
    g.scale(-1, 1)
    animation.draw(-100, 100)
    animation.draw(-200, 100, 36 * 4, 65 * 4)
    if (start < 0) limited.draw(-400, 100, 36 * 4, 65 * 4)
    manual.draw(-600, 100, 36 * 4, 65 * 4)
    pingPong.draw(-700, 100, 36 * 2, 65 * 2)
  }

  def main(args: Array[String]) {
    try {
      val appgc = new AppGameContainer(AnimationTest)
      appgc.setDisplayMode(800, 600, false)
      appgc.start()
    } catch {
      case ex: SlickException => logger.error(s"Failed during startup: ${ex.getMessage}", ex)
    }
  }

}

