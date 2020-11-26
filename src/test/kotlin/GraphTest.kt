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
        var neigh  = g.neighbours(n)
        assertTrue(neigh.contains(n2))
        assertTrue(neigh.contains(n3))
        assertTrue(neigh.size == 2)

        neigh = g.neighbours(n2)
        assertTrue(neigh.contains(n))
        assertTrue(neigh.size == 1)

        neigh = g.neighbours(n3)
        assertTrue(neigh.contains(n))
        assertTrue(neigh.size == 1)

        val n4 = Node<Int>(4, 0.0)
        g.addEdge(n4, n3)
        neigh = g.neighbours(n3)
        assertTrue(neigh.size == 2)
        assertTrue(neigh.contains(n4))
        assertTrue(neigh.contains(n))

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
        //assertTrue(neigh.contains(n))
        assertTrue(neigh.contains(n3))
        assertTrue(neigh.size == 2)

         neigh = g.neighbours(n3)
        assertTrue(neigh.contains(n2))
        assertTrue(neigh.contains(n4))
        assertTrue(neigh.size == 2)

        neigh = g.neighbours(n4)
        assertTrue(neigh.contains(n5))
        assertTrue(neigh.size == 2)

        neigh = g.neighbours(n5)
        assertTrue(neigh.contains(n))
        assertTrue(neigh.contains(n4))
        assertTrue(neigh.size == 2)
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
    fun path_cost(){
        val g = Graph<Int>()
        val n1 = Node<Int>(1, 0.0)
        val n2 = Node<Int>(2, 0.0)
        g.addEdge(n1, n2, 5.0)
        assertEquals(g.costToGo(n1, n2), 5.0)
        assertEquals(g.costToGo(n2, n1), 5.0)


    }
    @Test
    fun check_adjacent(){
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
    fun astar(){

       val h =  fun  (n1: Node<String>, _: Node<String>):Double{
                return when (n1.id) {
                    "A" -> 20.0
                    "B" -> 15.0
                    "C" -> 19.0
                    "F" -> 17.0
                    "E" -> 10.0
                    "G" -> 7.0
                    "D" -> 5.0
                    "X" -> 0.0


                    else -> 0.0
                }
        }
        val g = Graph<String>()

        g.addEdge(Node("A"), Node("B"), 2.0)
        g.addEdge(Node("A"), Node("C"), 1.0)
        g.addEdge(Node("A"), Node("E"), 2.0)
        g.addEdge(Node("B"), Node("E"),1.0)
        g.addEdge(Node("B"), Node("D"), 2.0)
        g.addEdge(Node("C"), Node("F"), 3.0)
        g.addEdge(Node("D"), Node("E"), 1.0)
        g.addEdge(Node("D"), Node("X"), 1.0)
        g.addEdge(Node("E"), Node("G"), 3.0)
        g.addEdge(Node("E"), Node("F"), 1.0)
        g.addEdge(Node("G"), Node("X"), 1.0)
        g.addEdge(Node("F"), Node("G"), 2.0)

        val p = aStar(g, Node("A"), Node("X"), h)
        val pd = dijkstra(g, "A","X")
        outputDot(g, pd)
        println(pd)
    }
    @Test
    fun dfs(){
        val g = Graph<Int>()
        g.addEdge(1, 2)
        g.addEdge(1, 7)
        g.addEdge(1, 8)
        g.addEdge(2, 3)
        g.addEdge(2, 6)
        g.addEdge(3, 4)
        g.addEdge(3, 5)
        g.addEdge(8, 9)
        g.addEdge(8, 12)
        g.addEdge(9, 10)
        g.addEdge(9, 11)


        var l = mutableListOf<Int>()
        //val p =  fun (s: Int)= println(s)
        val a = fun (s: Int) {
            l.add(s)
            println(s)
            return
        }
        depthFirstTraversal(Node(1), g, a)
        //all nodes visted
        assertEquals(12, l.size)
        l.mapIndexed { ix, it ->
            {
                assertEquals(ix + 1, it)
            }
        }

        outputDot(g, emptyList(), "dfs.dot")
    }
    @Test
    fun bfs(){
        val g = Graph<Int>()
        g.addEdge(1, 2)
        g.addEdge(1, 3)
        g.addEdge(1, 4)
        g.addEdge(2, 5)
        g.addEdge(2, 6)
        g.addEdge(5, 9)
        g.addEdge(5, 10)
        g.addEdge(4, 7)
        g.addEdge(4, 8)
        g.addEdge(7, 11)
        g.addEdge(7, 12)
        var l = mutableListOf<Int>()
        //val p =  fun (s: Int)= println(s)
        val a = fun (s: Int) {
            l.add(s)
            println(s)
            return
        }
        breadthFirstTraversal(Node<Int>(1), g, a)
        //all nodes visted and order ok
        assertEquals(12, l.size)
        l.mapIndexed { ix, it ->
            {
                assertEquals(ix + 1, it)
            }
        }
        outputDot(g, emptyList(), "bfs.dot")
    }

}
