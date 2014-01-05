package slick2dtest

import org.newdawn.slick._
import com.typesafe.scalalogging.slf4j.Logging
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object SnakeGame extends BasicGame("Snake") with Logging {

  private var snake = Snake(2, ArrayBuffer[Joint](Joint(30, 24), Joint(29, 24)))
  private var food = Food(1, ArrayBuffer.empty[(Int,Int)], 5000, 0.5)
  private var direction = Directions.Right

  def init(container: GameContainer) {}

  def update(container: GameContainer, delta: Int) {
    food.update(delta)
    println(food.food)
    if (container.getInput.isKeyDown(Input.KEY_DOWN)) direction = Directions.Down
    if (container.getInput.isKeyDown(Input.KEY_UP)) direction = Directions.Up
    if (container.getInput.isKeyDown(Input.KEY_LEFT)) direction = Directions.Left
    if (container.getInput.isKeyDown(Input.KEY_RIGHT)) direction = Directions.Right
    if (container.getInput.isKeyDown(Input.KEY_SPACE)) snake.lenght += 1

    val newHead = Joint(snake.joints.head.x, snake.joints.head.y)
    if (direction == Directions.Up) newHead.y -= 1
    if (direction == Directions.Down) newHead.y += 1
    if (direction == Directions.Left) newHead.x -= 1
    if (direction == Directions.Right) newHead.x += 1

    if (snake.joints.contains(newHead)) container.exit()
    snake.joints.prepend(newHead)

    val i = food.food.indexOf((newHead.x, newHead.y))
    if (i > -1) {
      food.food.remove(i)
      snake.lenght += 1
    }
    if (snake.joints.size > snake.lenght) snake.joints = snake.joints.dropRight(1)
    if (snake.joints.head.x < 0 || snake.joints.head.x > 64 || snake.joints.head.y < 0 || snake.joints.head.y > 49) container.exit()
  }

  def renderBoard(g: Graphics) {
    g.setColor(Color.white)
    for (x <- 50.to(700, 10)) g.drawLine(x, 50, x, 550)
    for (y <- 50.to(550, 10)) g.drawLine(50, y, 700, y)
  }

  def renderSnake(g: Graphics) {
    g.setColor(Color.white)
    snake.joints.map(joint => g.fillRect(50 + joint.x * 10, 50 + joint.y * 10, 10, 10))
  }

  def renderFood(g: Graphics) {
    g.setColor(Color.red)
    food.food.map(food => g.fillRect(50 + food._1 * 10, 50 + food._2 * 10, 10, 10))
  }

  def render(container: GameContainer, g: Graphics) {
    renderBoard(g)
    renderSnake(g)
    renderFood(g)
  }

  def main(args: Array[String]) {
    try {
      val container = new AppGameContainer(SnakeGame)
      container.setDisplayMode(800, 600, false)
      container.setTargetFrameRate(30)
      container.setMinimumLogicUpdateInterval(100)
      container.start()
    } catch {
      case ex: SlickException => logger.error(s"Failed during startup: ${ex.getMessage}", ex)
    }
  }
}

case class Joint(var x: Int, var y: Int)
case class Snake(var lenght: Int, var joints: ArrayBuffer[Joint])

case class Food(var maxFood: Int, var food: ArrayBuffer[(Int,Int)], var spawnInterval:Int, var chance: Double) {
  private var elapsedTime = 0
  def update(delta:Int) {
    elapsedTime += delta
    if (elapsedTime>spawnInterval) {
      if (Random.nextDouble()<=chance && food.size<maxFood) food += ((Random.nextInt(63), Random.nextInt(48)))
      elapsedTime = 0
    }
  }
}

object Directions extends Enumeration {
  type Directions = Value
  val Up, Down, Left, Right = Value
}