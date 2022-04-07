import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

class Node {
    String c;
    int count;
    Node left;
    Node right;
    
    boolean isLeaf(){
        return left == null && right == null;
    }
}

class HuffmanNodeComparator implements Comparator<Node> {
    
    public int compare(Node x, Node y) {
        return x.count - y.count;
    }
}


public class HuffmanCode{
    
    static Map<String, String> codes = new HashMap<>();
    
    public static void printCode(Node node, String code) {
        
        if (node.isLeaf()) {
            System.out.println(node.c + ": " + code);
            codes.put(String.valueOf(node.c), code);
            return;
        }
        
        printCode(node.left, code + "0");
        printCode(node.right, code + "1");
        
    }
    
    public static String compressed(String text) {
        
        String outputString ="";
        for (int i = 0; i < text.length(); i++) {
            outputString += codes.get(String.valueOf(text.charAt(i)));
        }
        return outputString;
    }

     public static void main(String []args){
        
        //Input your Text to convert
        String text = "lorem ipsum";
        
        Map<String, Integer> chars = new HashMap<>();
        
        for (int i = 0; i < text.length(); i++) {
            
            
            Integer count = chars.get(String.valueOf(text.charAt(i)));
            if (count == null) {
                count = 1;
            } else {
                count ++;
            }
            
            chars.put(String.valueOf(text.charAt(i)), count);
        }
        
        
        PriorityQueue<Node> queue = new PriorityQueue<Node>(chars.size(), new HuffmanNodeComparator());
        
        for (Map.Entry<String, Integer> entry : chars.entrySet()) {
            
            Node node = new Node();
            node.c = entry.getKey();
            node.count = entry.getValue();
            node.left = null;
            node.right = null;
            
            queue.add(node);
        }
        
        while(queue.size() > 1) {
            Node x = queue.peek();
            queue.poll();
            
            Node y = queue.peek();
            queue.poll();
            
            Node parent = new Node();
            parent.left = x;
            parent.right = y;
            parent.count = x.count + y.count;
            parent.c = "-";
            
            queue.add(parent);
        }
        
        printCode(queue.peek(), "");
        System.out.println("Original:" + text);
        System.out.println("Nr of bits: " + text.length() * 8);
        System.out.println("Codiert: " + compressed(text));
        System.out.println("Nr of bits: " + compressed(text).length());
        
        
        
     }
}
