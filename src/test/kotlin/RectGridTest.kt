import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.math.abs

internal class RectGridTes {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }
    @Test
    fun easy_path(){
        val g = RectGrid(50,50, penaliseDiagonal)
        val st = Node(Point(0,0), 0.0)
        val en = Node(Point(49,49), 0.0)
        //typealias Heuristic<T> = (Node<T>, Node<T>) -> Double
        val myF = fun (n1: Node<Point>, n2: Node<Point>) :Double {
          var d =
                 //sqrt(((n1.id.x - n2.id.x)*(n1.id.x - n2.id.x)  + ( n1.id.y - n2.id.y)*( n1.id.y - n2.id.y)).toDouble())
            abs(n1.id.x - n2.id.x)  +  abs( n1.id.y - n2.id.y)

            return d.toDouble()

        }
        val path = g.aStar(st, en, myF)
        print(path)
        g.printGrid(path)
    }

    @Test
    fun make_grid(){
        val g = RectGrid(2,2)
        print(g)
    }
}
