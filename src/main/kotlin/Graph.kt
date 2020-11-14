import java.util.*
import kotlin.collections.HashMap

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
typealias PathCostFunction = (Point, Point) -> Double
val fixedCostPath: PathCostFunction =  fun(_: Point, _: Point):Double = 1.0

class RectGrid(private val rows: Int, private val cols: Int,f: PathCostFunction = fixedCostPath) : Graph<Point>() {

    private val unitPoints = listOf<Point>(Point(1, 0), Point(0, -1), Point(-1, 0), Point(0, 1))
    init {
        //point for each r,c. and edges between each point and its U,D,L,R neighbours
        //iff the neighbour is within the grid
        for (x in 0..cols - 1){
            for(y in 0..rows - 1){
                val p = Point(x, y)
                println(p);
                println("****")
                for(unitP in unitPoints){
                    val neigh = p + unitP
                    if(inBounds(neigh)){

                        println(neigh)
                        addEdge(Node(p), Node(neigh), fixedCostPath(p, neigh))
                    }
                }
                println("-------------")
            }
        }
    }


    private fun inBounds(pn: Point): Boolean =  pn.x in 0..rows -1 &&  pn.y in 0..cols - 1

}
/**
 * Graph of node of T (Undirected)
 *
 * @param T
 * @constructor Create empty Graph
 */
open class Graph<T>(){

    private data class WeightedEdge<T>(val node: Node<T>, val weightToParent: Double)

    private val edges: HashMap<Node<T>, MutableCollection<WeightedEdge<T>>> = hashMapOf()

     fun AStar(start: Node<T>, goal: Node<T>, f: Heuristic<T>): Collection<Node<T>>{


         var cameFrom = mutableMapOf<Node<T>, Node<T>>()
         var costSoFar =  mutableMapOf<Node<T>, Double>()

         var frontier = PriorityQueue<PathNode<T>>()
         frontier.add(PathNode(start, 0.0))

         cameFrom[start] = start
         costSoFar[start] = 0.0

         while (frontier.count() > 0){

             var current = frontier.remove().node
             if(current == goal){


                 //unpick path from cameFrom
                 break
             }

             var ney = neighbours(current)
             for ( next in ney){
                 // 'cost of going from current to next'
                 println(costToGo(current, next))
                 val  newCost = costSoFar[current]!! + costToGo(current, next)
                 println(costSoFar.containsKey(next))
                 if( !costSoFar.containsKey(next) || newCost < costSoFar[next]!!){
                     costSoFar[next] = newCost

                     println("===")
                     println(newCost)
                     println(f(next, goal))
                     println("---------------")
                     val priority = newCost + f (next, goal)
                     val newF = PathNode(next, priority)
                     if (!frontier.contains(newF)){
                         frontier.add(newF)

                     }
                     cameFrom[next] = current
                 }

             }

         }
        val p = mutableSetOf( cameFrom.values).flatten()
         var path = mutableSetOf<Node<T>>()
        cameFrom.keys.forEach(){
            cameFrom!![it]?.let { it1 -> path.add(it1) }
        }
        return path

    }
    fun costToGo(from: Node<T>, to: Node<T>): Double{
        return  edges[from]!!.filter { it -> it.node == to }[0].weightToParent

    }
    /**
     * Add edge
     *
     * @param from
     * @param to
     */
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
      return (edges.keys union edges.values.flatten().map { it -> it.node }) //sets will remove dups.
    }

    /**
     * Adjacent
     *
     * @param n1
     * @param n2
     * @return
     */
    fun adjacent(n1: Node<T>, n2: Node<T>): Boolean{
        return edges.containsKey(n1) && edges[n1]!!.map { it -> it.node }.contains(n2)
                ||
               edges.containsKey(n2) && edges[n2]!!.map { it -> it.node }.contains(n1)
    }

    /**
     * Neighbours
     *
     * @param n
     * @return
     */
    fun neighbours(n: Node<T> ): Collection<Node<T>>{

        if(edges.containsKey(n)) {
            val nodes = edges[n]!!.toList().map { it -> it.node }
            return Collections.unmodifiableList(nodes)
        }
        return emptyList()
    }
}
