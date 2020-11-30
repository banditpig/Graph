import kotlin.collections.HashMap

/**
 * Node
 *
 * @param T
 * @property id
 * @property weight
 * @constructor Create empty Node
 */
data class Node<T>(val id: T, val weight: Double = 1.0) {
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
 * Path node
 *
 * @param T
 * @property node
 * @property value
 * @constructor Create empty Path node
 */
data class PathNode<T>(val node: Node<T>, val value: Double): Comparable<PathNode<T>>{

    override fun compareTo(other: PathNode<T>) = value.compareTo(other.value)
}

/**
 * Point
 *
 * @property x
 * @property y
 * @constructor Create empty Point
 */
data class Point(val x: Int, val y: Int){
    /**
     * Plus
     *
     * @param o
     * @return
     */
    operator fun plus(o: Point): Point = Point(x + o.x, y + o.y)

}

/**
 * Weighted edge
 *
 * @param T
 * @property node
 * @property weightToParent
 * @constructor Create empty Weighted edge
 */
data class WeightedEdge<T>(val node: Node<T>, val weightToParent: Double)


/**
 * Graph
 *
 * @param T
 * @constructor Create empty Graph
 */
open class Graph<T>{


    protected var edges: HashMap<Node<T>, MutableCollection<WeightedEdge<T>>> = hashMapOf()
    constructor()

    private constructor(edges: HashMap<Node<T>, MutableCollection<WeightedEdge<T>>> ){
        this.edges = edges
    }
    /**
     * Cost to go
     *
     * @param from
     * @param to
     * @return
     */
    fun costToGo(from: Node<T>, to: Node<T>): Double{
        return  edges[from]!!.filter { it.node == to }[0].weightToParent

    }

    /**
     * Add edge
     *
     * @param from
     * @param to
     * @param costFromTo
     */
    open fun addEdge(from: Node<T>, to: Node<T>, costFromTo: Double = 1.0){

        insertEdge(from, to, costFromTo)
        //This is an undirected graph. So be explicit in that there's also an edge to-from with the same cost.
        //A directed graph would override addEdge.
        insertEdge(to, from, costFromTo)
    }

    /**
     * Add edge
     *
     * @param from
     * @param to
     * @param costFromTo
     */
    fun addEdge(from: T, to: T, costFromTo: Double = 1.0){
        addEdge(Node(from), Node(to), costFromTo)
    }

    fun insertEdge(from: Node<T>, to: Node<T>, costFromTo: Double) {
        when (edges.contains(from)) {
            true -> edges[from]!!.add(WeightedEdge(to, costFromTo) )
            false -> {
                edges[from] = mutableSetOf (WeightedEdge(to, costFromTo))
            }
        }
    }


    /**
     * Nodes
     *
     * @return
     */
    fun nodes() : Collection<Node<T>> {
      return (edges.keys union edges.values.flatten().map { it.node }) //sets will remove duplicates.
    }

    /**
     * Edges for
     *
     * @param n
     * @return
     */
    fun edgesFor(n: Node<T>): Collection<WeightedEdge<T>> =
         edges.filterKeys { it == n }
            .map { kv -> kv.value }
            .flatten()



    /**
     * Adjacent
     *
     * @param n1
     * @param n2
     * @return
     */
    open fun adjacent(n1: Node<T>, n2: Node<T>): Boolean{
       return neighbours(n1).contains(n2) ||  neighbours(n2).contains(n1)
    }

    /**
     * Neighbours
     *
     * @param n
     * @return
     */
    fun neighbours(n: Node<T> ): Collection<Node<T>> {
       return edges.filterKeys { it == n }
            .map { kv -> kv.value }
            .flatten()
            .map { it.node }
    }

    fun <R> map(tr: (T) -> R): Graph<R>{

        val ks = edges.keys.map { Node(tr(it.id)) }
        val vs = edges.values
                .map { l -> l.map { WeightedEdge(Node(tr(it.node.id)),it.weightToParent) }}
        val edgesR:  HashMap<Node<R>, MutableCollection<WeightedEdge<R>>> = hashMapOf()
        ks.forEachIndexed { index, node -> edgesR[node] = vs[index].toMutableList()  }
        return Graph(edgesR)
    }
    fun  <R> fold(initial: R, op: (acc: R, T) -> R): R {
        val op1 = fun (acc: R, nd: Node<T>) = op(acc, nd.id)
        return nodes().fold(initial, op1)
    }




}
