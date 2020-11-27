
class Tree<T>(private val root: Node<T>) : DiGraph<T>(){
    constructor(root: T) : this(Node(root))

    fun addChildTo(parent: Node<T>, child: Node<T>) =
        addEdge(parent, child)

    fun addChildTo(parent: T, child: T) =
        addEdge(parent, child)

    fun children(parent: T) =
        children(Node(parent))

    fun children(parent: Node<T>): Collection<Node<T>> =
        neighbours(parent)

    fun countChilden(parent: Node<T>) =
        children(parent).size

    fun outDegree(n: Node<T>):Int = countChilden(n)
    fun outDegree(t: T):Int = countChilden(Node(t))

    fun parents(n: Node<T>): Collection<Node<T>>{
       return  edges.filter { kv -> kv.value.map{it.node}
               .contains(n) }
               .map{it.key}
    }
    fun inDegree(n: Node<T>):Int = parents(n).size
    fun inDegree(t: T):Int = parents(Node(t)).size

    fun isLeaf(n: Node<T>):Boolean = outDegree(n) == 0
    fun isLeaf(t: T):Boolean = outDegree(Node(t)) == 0


}