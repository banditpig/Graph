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
fun processLine(line: String):List<Triple<String, String, String>>{
   return line.split(",").chunked(3).map {makeTriple(it)  }
}

/**
 * Process for all lines
 *
 * @param allLines
 * @return
 */
fun processForAllLines(allLines: String):List<List<Triple<String, String, String>>>{
   return allLines.split("*")
            .map {processLine(it)  }
}

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
    for(n in g.nodes()) {
        val id = n.id.toString()
        dot.append(id)
        if (path.contains(n)) {
            dot.append(" [color=red]").append("\n")
        } else {
            dot.append("\n")
        }
    }
    //and loop again - this time adding edges.
    for(n in g.nodes()){
        val id = n.id.toString()
          for (c in g.edgesFor(n)){
            val edgeStr = id + " -- " + c.node.id.toString()
            val edgeStrRev = c.node.id.toString() + " -- " + id
            if (!doneEdges.contains(edgeStr) && !doneEdges.contains(edgeStrRev)){
                dot.append(edgeStr)
                val label = c.weightToParent.toString()
                if (path.contains(c.node) && path.contains(n)){
                    dot.append(" [label = $label, color=red ];")
                }else {
                    dot.append(" [label = $label];")
                }

                dot.append("\n")
                doneEdges.add(edgeStr)
                doneEdges.add(edgeStrRev)
            }


        }

    }
    dot.append("}")
    println(dot.toString())
    File(fName).writeText(dot.toString())
}