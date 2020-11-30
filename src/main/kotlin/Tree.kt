
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

    fun countChildren(node: Node<T>) =
            children(node).size
    fun countChildren(node: T) =
            countChildren(Node(node))




    fun isLeaf(n: Node<T>):Boolean = outDegree(n) == 0
    fun isLeaf(t: T):Boolean = outDegree(Node(t)) == 0


}