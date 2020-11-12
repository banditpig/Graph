import java.util.*
import java.util.function.DoubleUnaryOperator
import kotlin.collections.HashMap

/**
 * Node
 *
 * @param T
 * @property id
 * @property weight
 * @constructor Create empty Node
 */
data class Node<T>(val id: T, val weight: Double) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node<*>

        if (id != other.id) return false
        if (weight != other.weight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + weight.hashCode()
        return result
    }
}

/**
 * Path node. Used as a wrapper of Node during path finding.
 *
 * @param T
 * @property node
 * @property value
 * @constructor Create empty Path node
 */
data class PathNode<T>(val node: Node<T>, val value: Double): Comparable<PathNode<T>>{

    override fun compareTo(other: PathNode<T>) = value.compareTo(other.value)

}

typealias Point = Pair<Int, Int>
typealias Heuristic<T> = (Node<T>, Node<T>) -> Double
typealias Grid  = Graph<Node<Point>>


/**
 * Graph of node of T (Undirected)
 *
 * @param T
 * @constructor Create empty Graph
 */
class Graph<T>(){

    private val edges: HashMap<Node<T>, MutableCollection<Node<T>>  > = hashMapOf<Node<T>, MutableCollection<Node<T>>  >()

    fun AStar(start: Node<T>, end: Node<T>, f: Heuristic<T>): Maybe<List<Node<T>>>{

       return Maybe.Just(emptyList<Node<T>>())

    }
    /**
     * Add edge
     *
     * @param from
     * @param to
     */
    fun addEdge(from: Node<T>, to: Node<T>){

        fun insertEdge(from: Node<T>, to: Node<T>) {
            when (edges.contains(from)) {
                true -> edges[from]!!.add(to)
                false -> {
                    edges[from] = mutableListOf(to)
                }
            }
        }
        //This is an undirected graph.
        insertEdge(from, to)
        insertEdge(to, from)
    }



    /**
     * Nodes
     *
     * @return
     */
    fun nodes() : Collection<Node<T>> {
      return (edges.keys union edges.values.flatten()) //sets will remove dups.
    }

    /**
     * Adjacent
     *
     * @param n1
     * @param n2
     * @return
     */
    fun adjacent(n1: Node<T>, n2: Node<T>): Boolean{
        return edges.containsKey(n1) && edges[n1]!!.contains(n2)
                ||
               edges.containsKey(n2) && edges[n2]!!.contains(n1)
    }

    /**
     * Neighbours
     *
     * @param n
     * @return
     */
    fun neighbours(n: Node<T> ): Maybe<Collection<Node<T>>>{

        if(edges.containsKey(n)) {
           return Maybe.Just(Collections.unmodifiableList(edges[n]!!.toList()))
        }
        return Maybe.None
    }
}
