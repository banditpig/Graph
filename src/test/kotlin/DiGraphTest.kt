import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class DiGraphTest {

    @Test
    fun check_all_nodes() {

        //nodes are counted irrespective of edges
        //so shouldbe same as in  aGraph
        val allNodes = mutableSetOf<Node<Int>>()
        val g = DiGraph<Int>()

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
    fun traverse_nodes() {


        val g = DiGraph<Int>()

        val n = Node<Int>(1, 0.0)
        val n2 = Node<Int>(2, 0.0)
        val n3 = Node<Int>(3, 0.0)

        g.addEdge(n, n2)
        g.addEdge(n, n3)

        assertTrue(g.canTraverse(n, n2))
        assertTrue(g.canTraverse(n, n3))

        assertFalse(g.canTraverse(n2, n))
        assertFalse(g.canTraverse(n3, n))
        assertFalse(g.canTraverse(n3, n2))
        assertFalse(g.canTraverse(n2, n3))

    }

}