import java.util.*
import kotlin.collections.HashMap
import kotlin.math.abs

/**
 * Node
 *
 * @param T
 * @property id
 * @property weight
 * @constructor Create empty Node
 */
data class Node<T>(val id: T, val weight: Double = 0.0) {
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
data class Point(val x: Int, val y: Int){
     operator fun plus(o: Point): Point = Point(x + o.x, y + o.y)

}

typealias Heuristic<T> = (Node<T>, Node<T>) -> Double
typealias PathCostFunction<T> = (Node<T>, Node<T>) -> Double
val fixedCostPath: PathCostFunction<Point> =  fun(_: Node<Point>, _: Node<Point>):Double = 1.0
val penaliseDiagonal : PathCostFunction<Point> =  fun(frm: Node<Point>, to: Node<Point>):Double{
    val xTo = to.id.x
    val yTo = to.id.y
    val xFr = frm.id.x
    val yFr = frm.id.y
    if (abs(xTo - xFr) > 0 && abs(yTo - yFr) >0){
        return 20.0
    }

    return 1.0
}
class RectGrid(private val xRange: Int, private val yRange: Int, f: PathCostFunction<Point> = fixedCostPath) : Graph<Point>() {

    private val unitPoints = listOf(
            Point(1, 0), Point(0, -1), Point(-1, 0), Point(0, 1),
            Point(-1, -1),Point(1, -1),Point(1, 1),Point(-1,1))
    init {
        //point for each r,c. and edges between each point and its U,D,L,R neighbours
        //iff the neighbour is within the grid
        for (x in 0 until xRange){
            for(y in 0 until yRange){
                val p = Point(x, y)

                for(unitP in unitPoints){
                    val neigh = p + unitP
                    if(inBounds(neigh)){
                        addEdge(Node(p), Node(neigh), f(Node(p), Node(neigh)))
                    }
                }
            }
        }
    }

    fun printGrid(path: Collection<Node<Point>> = emptyList()){
        println()
        for (x in 0 until xRange){
            for(y in 0 until yRange){
                val pn = Node(Point(x, y))
                if (path.contains(pn)){
                    print(".")
                }else {
                    print("#")
                }
            }
            println()
        }
        println()

    }
    private fun inBounds(pn: Point): Boolean =  pn.x in 0 until xRange &&  pn.y in 0 until yRange

}
/**
 * Graph of node of T (Undirected)
 *
 * @param T
 * @constructor Create empty Graph
 */
open class Graph<T> {

    private data class WeightedEdge<T>(val node: Node<T>, val weightToParent: Double)

    private val edges: HashMap<Node<T>, MutableCollection<WeightedEdge<T>>> = hashMapOf()

    fun aStar(start: Node<T>, goal: Node<T>, f: Heuristic<T>): Collection<Node<T>>{


         val cameFrom = mutableMapOf<Node<T>, Node<T>>()
         val costSoFar =  mutableMapOf<Node<T>, Double>()

         val frontier = PriorityQueue<PathNode<T>>()
         frontier.add(PathNode(start, 0.0))

         cameFrom[start] = start
         costSoFar[start] = 0.0

         while (!frontier.isEmpty()){

             val current = frontier.remove().node
             if(current == goal){

                 tailrec fun unpick(acc: MutableList<Node<T>>,  n: Node<T>){
                     acc.add(n)
                     if(n == start) return
                     unpick(acc, cameFrom.remove(n)!!)
                 }
                 val path =  mutableListOf(goal)
                 val nd = cameFrom.remove(goal)
                 unpick(path, nd!!)
                 return path
             }

             for ( next in neighbours(current)){
                 // 'cost of going from current to next'
                 val  newCost = costSoFar[current]!! + costToGo(current, next)
                 if( !costSoFar.containsKey(next) || newCost < costSoFar[next]!!){
                     costSoFar[next] = newCost

                     val priority = newCost + f (next, goal)
                     val newF = PathNode(next, priority)
                     if (!frontier.contains(newF)){
                         frontier.add(newF)

                     }
                     cameFrom[next] = current
                 }
             }
         }

        return emptyList()

    }
    fun costToGo(from: Node<T>, to: Node<T>): Double{
        return  edges[from]!!.filter { it.node == to }[0].weightToParent

    }

    fun addEdge(from: Node<T>, to: Node<T>, costFromTo: Double = 1.0){

        insertEdge(from, to, costFromTo)
        //This is an undirected graph. So be explicit in that there's also and edge to-from with the same cost.
        //A directed graph would override addEdge.
        insertEdge(to, from, costFromTo)
    }
    private fun insertEdge(from: Node<T>, to: Node<T>, costFromTo: Double) {
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
      return (edges.keys union edges.values.flatten().map { it.node }) //sets will remove dups.
    }

    /**
     * Adjacent
     *
     * @param n1
     * @param n2
     * @return
     */
    fun adjacent(n1: Node<T>, n2: Node<T>): Boolean{
        return edges.containsKey(n1) && edges[n1]!!.map { it.node }.contains(n2)
                ||
               edges.containsKey(n2) && edges[n2]!!.map { it.node }.contains(n1)
    }

    /**
     * Neighbours
     *
     * @param n
     * @return
     */
    fun neighbours(n: Node<T> ): Collection<Node<T>>{

        if(edges.containsKey(n)) {
            val nodes = edges[n]!!.toList().map { it.node }
            return Collections.unmodifiableList(nodes)
        }
        return emptyList()
    }
}
