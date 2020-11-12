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
    fun make_ring() {

        val g = Graph<Int>()

        val n = Node<Int>(1, 0.0)
        val n2 = Node<Int>(2, 0.0)
        val n3 = Node<Int>(3, 0.0)
        val n4 = Node<Int>(4, 0.0)
        val n5 = Node<Int>(5, 0.0)
        val allNodes = mutableSetOf<Node<Int>>(n, n2, n3, n4, n5)
        g.addEdge(n, n2)
        g.addEdge(n2, n3)
        g.addEdge(n3, n4)
        g.addEdge(n4, n5)
        g.addEdge(n5, n)

        assertTrue(g.nodes().containsAll(allNodes))
        assertTrue(g.nodes().size == allNodes.size)

        //check neigbours
        var neigh = g.neighbours(n2)
        when (neigh) {
            is Maybe.Just -> {
                assertTrue(neigh.value.contains(n))
                assertTrue(neigh.value.contains(n3))
                assertTrue(neigh.value.size == 2)
            }
            else -> {
            }
        }
         neigh = g.neighbours(n3)
        when (neigh) {
            is Maybe.Just -> {
                assertTrue(neigh.value.contains(n2))
                assertTrue(neigh.value.contains(n4))
                assertTrue(neigh.value.size == 2)
            }
            else -> {
            }
        }
        neigh = g.neighbours(n4)
        when (neigh) {
            is Maybe.Just -> {
                assertTrue(neigh.value.contains(n3))
                assertTrue(neigh.value.contains(n5))
                assertTrue(neigh.value.size == 2)
            }
            else -> {
            }
        }

        neigh = g.neighbours(n5)
        when (neigh) {
            is Maybe.Just -> {
                assertTrue(neigh.value.contains(n))
                assertTrue(neigh.value.contains(n4))
                assertTrue(neigh.value.size == 2)
            }
            else -> {
            }
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
