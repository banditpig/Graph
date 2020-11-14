import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.contracts.contract
import kotlin.math.abs
import kotlin.math.sqrt

internal class RectGridTes {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }
    @Test
    fun easy_path(){
        val g = RectGrid(92,123)
        val st = Node(Point(0,0), 0.0)
        val en = Node(Point(91,122), 0.0)
        //typealias Heuristic<T> = (Node<T>, Node<T>) -> Double
        val myF = fun (n1: Node<Point>, n2: Node<Point>) :Double {
          var d = sqrt(((n1.id.x - n2.id.x) * (n1.id.x - n2.id.x) + ( n1.id.y - n2.id.y)*( n1.id.y - n2.id.y)).toDouble())
          return d

        }
        g.AStar(st, en, myF)
    }

    @Test
    fun make_grid(){
        val g = RectGrid(2,2)
        print(g)
    }
}
