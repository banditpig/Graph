/**
 * Rect grid
 *
 * @property xRange
 * @property yRange
 * @constructor
 *
 * @param f
 */
class RectGrid(private val xRange: Int, private val yRange: Int, f: PathCostFunction<Point> = fixedCostPath) : Graph<Point>() {

    private val unitPoints = listOf(
            Point(1, 0), Point(0, -1), Point(-1, 0), Point(0, 1),
            Point(-1, -1),Point(1, -1),Point(1, 1),Point(-1,1))
    init {
        //point for each r,c. and edges between each point and its U,D,L,R neighbours
        //iff the neighbour is within the grid
        (0 until xRange).forEach { x ->
            (0 until yRange).forEach { y ->
                val p = Point(x, y)

                unitPoints.map { p + it }
                          .filter { inBounds(it) }
                          .forEach { addEdge(Node(p), Node(it), f(Node(p), Node(it))) }
            }
        }
    }

    /**
     * Print grid
     *
     * @param path
     */
    fun printGrid(path: Collection<Node<Point>> = emptyList()){
        println()
        fun decideWhichCharPrint(it: Node<Point>) {
            if (path.contains(it)) {
                print(".")
            } else {
                print("#")
            }
        }

        (0 until xRange).forEach { x ->
            (0 until yRange)
                    .map { Node(Point(x, it)) }
                    .forEach {
                        decideWhichCharPrint(it)
                    }
            println()
        }
        println()

    }
    private fun inBounds(pn: Point): Boolean =  pn.x in 0 until xRange &&  pn.y in 0 until yRange

}
