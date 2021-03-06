import java.util.*
import kotlin.math.abs
import java.util.Queue as Queue

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

/**
 * Depth first traversal
 *
 * @param T
 * @param node
 * @param g
 * @param nodeFunc
 * @receiver
 */
fun <T> depthFirstTraversal(node: Node<T>, g: Graph<T>, nodeFunc: (T) -> Unit){

    fun dfs(node: Node<T>, visited: MutableSet<Node<T>>){
        nodeFunc(node.id)
        visited.add(node)
        g.neighbours(node)
                .filter { !visited.contains(it)  }

                .map {  ; dfs(it, visited) }
    }
    dfs(node, mutableSetOf())
}

/**
 * Breadth first traversal
 *
 * @param T
 * @param node
 * @param g
 * @param nodeFunc
 * @receiver
 */
fun <T> breadthFirstTraversal(node: Node<T>, g: Graph<T>,
                              nodeFunc: (T) -> Unit){


    tailrec fun bfs(q: Queue<Node<T>>, visited: MutableSet<Node<T>>){
        if (q.isEmpty())  return

        val nd = q.remove()
        nodeFunc(nd.id)

        g.neighbours(nd)
                .filter { !visited.contains(it)}
                .map { q.add(it)
                       visited.add(it) }
        bfs(q, visited)

    }

    val q = LinkedList<Node<T>>().apply { add(node) }
    bfs(q, mutableSetOf<Node<T>>().apply { add(node) })
}

/**
 * A star
 *
 * @param T
 * @param g
 * @param start
 * @param goal
 * @param costFunc
 * @receiver
 * @return
 */
fun <T> aStar(g: Graph<T>, start: T, goal: T, costFunc: (T, T) -> Double): Collection<Node<T>>  {
    val nodeCostFunc = fun (n1: Node<T>, n2: Node<T>):Double = costFunc(n1.id, n2.id)
    return aStar(g, Node(start), Node(goal), nodeCostFunc)
}

/**
 * Dijkstra
 *
 * @param T
 * @param g
 * @param start
 * @param goal
 * @return
 */
fun <T> dijkstra (g: Graph<T>, start: T, goal: T): Collection<Node<T>> {
    return dijkstra(g , Node(start), Node(goal))
}

/**
 * Dijkstra
 *
 * @param T
 * @param g
 * @param start
 * @param goal
 * @return
 */
fun <T> dijkstra (g: Graph<T>, start: Node<T>, goal: Node<T>): Collection<Node<T>> {
    val zeroHeuristic = fun(_: Node<T>, _: Node<T>): Double = 0.0
    return aStar(g, start, goal, zeroHeuristic)
}

/**
 * Astar
 *
 * @param T
 * @param g
 * @param start
 * @param goal
 * @param f
 * @return
 */
fun <T> aStar(g: Graph<T>, start: Node<T>, goal: Node<T>, f: Heuristic<T>): Collection<Node<T>>{


    val cameFrom = mutableMapOf<Node<T>, Node<T>>()
    val costSoFar =  mutableMapOf<Node<T>, Double>()

    val openSet = PriorityQueue<PathNode<T>>()
    openSet.add(PathNode(start, 0.0))

    cameFrom[start] = start
    costSoFar[start] = 0.0

    while (!openSet.isEmpty()){

        val current = openSet.remove().node
        if(current == goal){

            tailrec fun unpick(acc: MutableList<Node<T>>,  n: Node<T>){
                acc.add(n)
                if(n == start) return
                unpick(acc, cameFrom.remove(n)!!)
            }
            val path =  mutableListOf(goal)

            unpick(path,cameFrom.remove(goal)!! )
            return path.reversed()
        }

        for ( next in g.neighbours(current)){
            // 'cost of going from current to next'
            val  newCost = costSoFar[current]!! + g.costToGo(current, next)
            if( !costSoFar.containsKey(next) || newCost < costSoFar[next]!!){
                costSoFar[next] = newCost

                val priority = newCost + f (next, goal)
                val newF = PathNode(next, priority)
                if (!openSet.contains(newF)){
                    openSet.add(newF)

                }
                cameFrom[next] = current
            }
        }
    }

    return emptyList()

}

