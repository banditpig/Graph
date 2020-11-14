import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class PathNodeTest {
    @Test
    fun ordering() {

        val pn = PathNode(Node<Int>(1, 0.0), 1.0)
        val pn2 = PathNode(Node<Int>(2, 0.0), 2.0)
        val pn3 = PathNode(Node<Int>(3, 0.0), 3.0)
        val pn4 = PathNode(Node<Int>(4, 0.0), 4.0)
        val pn5 = PathNode(Node<Int>(5, 0.0), 5.0)

        assertTrue(pn < pn2)
        assertTrue(pn2 < pn3)
        assertTrue(pn3 < pn4)
        assertTrue(pn4 < pn5)

    }
    @Test
    fun queuing() {

        val pn = PathNode(Node<Int>(1, 0.0), 1.0)
        val pn2 = PathNode(Node<Int>(2, 0.0), 2.0)
        val pn3 = PathNode(Node<Int>(3, 0.0), 3.0)
        val pn4 = PathNode(Node<Int>(4, 0.0), 4.0)
        val pn5 = PathNode(Node<Int>(5, 0.0), 5.0)

        var all = listOf( pn,pn2,pn3,pn4,pn5);
        val qu = PriorityQueue<PathNode<Int>>()
        all = all.shuffled()
        qu.addAll(all)

        assertTrue(qu.remove() == pn)
        assertTrue(qu.remove() == pn2)
        assertTrue(qu.remove() == pn3)
        assertTrue(qu.remove() == pn4)
        assertTrue(qu.remove() == pn5)
        assertTrue(qu.isEmpty())

        all = all.shuffled()
        qu.addAll(all)

        assertTrue(qu.remove() == pn)
        assertTrue(qu.remove() == pn2)
        assertTrue(qu.remove() == pn3)
        assertTrue(qu.remove() == pn4)
        assertTrue(qu.remove() == pn5)
        assertTrue(qu.isEmpty())

        all = all.shuffled()
        qu.addAll(all)

        assertTrue(qu.remove() == pn)
        assertTrue(qu.remove() == pn2)
        assertTrue(qu.remove() == pn3)
        assertTrue(qu.remove() == pn4)
        assertTrue(qu.remove() == pn5)
        assertTrue(qu.isEmpty())




    }

}