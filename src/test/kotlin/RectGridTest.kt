import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.test.assertTrue

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
        val st = Node(Point(0,0))
        val en = Node(Point(49,49))
        //typealias Heuristic<T> = (Node<T>, Node<T>) -> Double
        val myF = fun (n1: Node<Point>, n2: Node<Point>) :Double =
                abs(n1.id.x - n2.id.x).toDouble()  +  abs( n1.id.y - n2.id.y).toDouble()

        val path = aStar(g, st, en, myF)
        assertTrue(path.contains(st))
        assertTrue(path.contains(en))
        assertTrue(path.size > 2)

        print(path)
        g.printGrid(path)
    }

}
