import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.test.asserter

internal class GraphTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }
    @Test
    fun neighbours(){
        val allNodes = mutableSetOf<Node<Int>>()
        val g = Graph<Int>()

        val n = Node<Int>(1, 0.0)
        val n2 = Node<Int>(2, 0.0)
        val n3 = Node<Int>(3, 0.0)

        g.addEdge(n, n2)
        g.addEdge(n, n3)
        allNodes.add(n)
        allNodes.add(n2)
        allNodes.add(n3)
        val neigh  = g.neighbours(n)
        when (neigh) {
            is Maybe.Just -> {
                assertTrue(neigh.value.contains(n2))
                assertTrue(neigh.value.contains(n3))
                assertTrue(neigh.value.size == 2)


            }
            else -> {}
        }
    }

    @Test
    fun add_1_node_linked_to_2_nodes() {
        val allNodes = mutableSetOf<Node<Int>>()
        val g = Graph<Int>()

        val n = Node<Int>(1, 0.0)
        val n2 = Node<Int>(2, 0.0)
        val n3 = Node<Int>(3, 0.0)

        g.addEdge(n, n2)
        g.addEdge(n, n3)
        allNodes.add(n)
        allNodes.add(n2)
        allNodes.add(n3)

        assertEquals(3, g.nodes().size)
        assertEquals(allNodes, g.nodes())


    }
    @Test
    fun check_adjacent(){
        val allNodes = mutableSetOf<Node<Int>>()
        val g = Graph<Int>()
        val n = Node<Int>(1, 0.0)
        val n2 = Node<Int>(2, 0.0)
        g.addEdge(n, n2)

        val n3 = Node<Int>(3, 0.0)
        val n4 = Node<Int>(4, 0.0)
        g.addEdge(n3, n4)

        assertTrue(g.adjacent(n, n2))
        assertTrue(g.adjacent(n2, n))

        assertTrue(g.adjacent(n3, n4))
        assertTrue(g.adjacent(n4, n3))

        assertFalse(g.adjacent(n, n3))
        assertFalse(g.adjacent(n3, n))
        assertFalse(g.adjacent(n, n4))


    }
    @Test
    fun add_1_edge() {
        val allNodes = mutableSetOf<Node<Int>>()
        val g = Graph<Int>()

        val n = Node<Int>(1, 0.0)
        val n2 = Node<Int>(2, 0.0)
        g.addEdge(n, n2)

        allNodes.add(n)
        allNodes.add(n2)

        assertEquals(2, g.nodes().size)
        assertEquals(allNodes, g.nodes())


    }

    @Test
    fun nodes() {
    }
}
