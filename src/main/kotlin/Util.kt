import java.io.File

/**
 * Maybe
 *
 * @param T
 * @constructor Create empty Maybe
 */
sealed class Maybe<out T> {
    object None : Maybe<Nothing>()

    /**
     * Just
     *
     * @param T
     * @property value
     * @constructor Create empty Just
     */
    data class Just<T>(val value: T) : Maybe<T>()
}

/**
 * Make triple
 *
 * @param three
 * @return
 */
fun makeTriple(three: List<String>):Triple<String, String, String>{
    return Triple(three[0], three[1],three[2])
}

/**
 * Process line
 *
 * @param line
 * @return list of triples
 */
fun processLine(line: String):List<Triple<String, String, String>> =
    line.split(",").chunked(3).map {makeTriple(it)
}

/**
 * Process for all lines
 *
 * @param allLines
 * @return
 */
fun processForAllLines(allLines: String):List<List<Triple<String, String, String>>> =
    allLines.split("*").map {processLine(it)  }


/**
 * Graph from string
 *
 * @param str
 * @return
 */
fun graphFromString(str: String):Graph<String>{

    //Format is:
    //parent node and its weight and then a comma space
    // - eg a,1.0,,
    //then any number of children given as a triple
    //<node name, node weight, edge weight ie cost to parent>
    //eg b,1.0,1,c,1.0,2.0,d,1.0,3
    //finally a termination char "*"
    //  so have...  a,1.0,,b,1.0,1,c,1.0,2.0,d,1.0,3*
    val graph = Graph<String>()
    val allTriples = processForAllLines(str)
    allTriples.forEach { tr ->
    //the first triple is really only a pair.
        val parent = Node(tr[0].first, tr[0].second.toDouble())
        tr.slice(1 until tr.size).forEach { edg ->
            val child = Node(edg.first,edg.second.toDouble())
            graph.addEdge(parent, child, edg.third.toDouble() )
        }
    }
    return graph
}

/**
 * Output dot
 *
 * @param T
 * @param g
 * @param path
 * @param fName
 */
fun <T> outputDot(g: Graph<T>, path: Collection<Node<T>> = emptyList(), fName: String = "graph.dot") {

    val gName = "G"
    var doneEdges = mutableSetOf<String>()
    var dot = StringBuilder("graph $gName {").append("\n")

    fun buildNodes(sb: StringBuilder) {
        fun nodeFunc(sb: StringBuilder, n: Node<T>):StringBuilder{
            sb.append(n.id.toString())
            if (path.contains(n)) {
                sb.append(" [color=red]")
            }

            return sb.append("\n")
        }
        g.nodes().fold(initial = sb, operation = { acc, node -> nodeFunc(acc, node)})
    }

    fun buildEdges(sb: StringBuilder) {
        g.nodes().forEach { n ->
            val id = n.id.toString()
            fun doEdge(str: StringBuilder,c: WeightedEdge<T>) : StringBuilder {
                val edgeStr = id + " -- " + c.node.id.toString()
                val edgeStrRev = c.node.id.toString() + " -- " + id
                if (!doneEdges.contains(edgeStr) && !doneEdges.contains(edgeStrRev)) {
                    str.append(edgeStr)
                    val label = c.weightToParent.toString()
                    if (path.contains(c.node) && path.contains(n)) {
                        str.append(" [label = $label, color=red ];")
                    } else {
                        str.append(" [label = $label];")
                    }
                    str.append("\n")
                    doneEdges.add(edgeStr)
                    doneEdges.add(edgeStrRev)
                }
                return str
            }
            g.edgesFor(n).fold(sb, { acc, c -> doEdge(acc, c)})
        }
        sb.append("}")
    }
    buildNodes(dot)
    buildEdges(dot)

    File(fName).writeText(dot.toString())
}