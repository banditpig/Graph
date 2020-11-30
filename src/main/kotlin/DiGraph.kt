/**
 * Di graph
 *
 * @param T
 * @constructor Create empty Di graph
 */
open class DiGraph<T> : Graph<T>(){

    override fun addEdge(from: Node<T>, to: Node<T>, costFromTo: Double){

        insertEdge(from, to, costFromTo)

    }

    /**
     * Add edge
     *
     * @param from
     * @param to
     */
    fun addEdge(from: Node<T>, to: Node<T>) {
         addEdge(from, to, 1.0)
    }

    override fun adjacent(n1: Node<T>, n2: Node<T>): Boolean{
        return edges.containsKey(n1) && edges[n1]!!.map { it.node }.contains(n2)

    }

    /**
     * Can traverse
     *
     * @param from
     * @param to
     * @return
     */
    fun canTraverse(from: Node<T>, to: Node<T>): Boolean =
        edges.containsKey(from) && edges[from]!!.map { it.node }.contains(to)

    fun inDegree(n: Node<T>):Int = inNodes(n).size
    fun inDegree(t: T):Int = inNodes(Node(t)).size
    fun outDegree(n: Node<T>):Int = outNodes(n).size
    fun outDegree(t: T):Int = outNodes(Node(t)).size
    fun inNodes(n: Node<T>): Collection<Node<T>>{
        return  edges.filter { kv -> kv.value.map{it.node}
                .contains(n) }
                .map{it.key}
    }
    fun outNodes(parent: Node<T>): Collection<Node<T>> =
            neighbours(parent)



}
