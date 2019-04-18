package greedy.huffman;

/**
 * @author ellin
 * @since 2019/03/28
 */
public class Node {

    public String charactor;

    public Integer frequency;

    public Node left;

    public Node right;

    public Node parent;

    public Integer width;

    public Node() {}

    public Node(String charactor, Integer frequency) {
        this.charactor = charactor;
        this.frequency = frequency;
    }

}
