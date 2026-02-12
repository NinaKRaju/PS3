import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HuffmanCodeTree implements Huffman{


    @Override
    public Map<Character, Long> countFrequencies(String pathName) throws IOException {
        Map<Character,Long> result = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(pathName));
        String line;
        while ((line = reader.readLine()) != null) {
            for(int i = 0; i<line.length();i++){
                Character c = line.charAt(i);
                if (!result.containsKey(c)) {
                    result.put(c, (long) 1);
                }
                else{
                    result.put(c,result.get(c)+1);
                }
            }
        }
        return result;
    }

    @Override
    public BinaryTree<CodeTreeElement> makeCodeTree(Map<Character, Long> frequencies) {
        PriorityQueue<BinaryTree<CodeTreeElement>> pq = new PriorityQueue<>(new TreeComparator());
        //returns if file is empty
        if(frequencies.isEmpty()){
            return null;
        }
        for(Character key: frequencies.keySet()){
            //creates a new binary tree for each character and adds it to the priority tree
            CodeTreeElement current = new CodeTreeElement(frequencies.get(key), key);
            BinaryTree<CodeTreeElement> currentNode = new BinaryTree<>(current);
            pq.add(currentNode);

        }

        //huffman algorithm
        while(pq.size()>1){
            //creates a binary tree for left node and sets it equal to the lowest freq tree in queue
            BinaryTree<CodeTreeElement> left = pq.peek();
            pq.remove(pq.peek());
            Long rootFreq = left.getData().getFrequency();
            //same for second element in queue, assign to right
            BinaryTree<CodeTreeElement> right = pq.peek();
            pq.remove(pq.peek());
            rootFreq += right.getData().getFrequency();
            //creates a root node with freq= combined left and right, children are left ad right
            BinaryTree<CodeTreeElement> newTree = new BinaryTree<CodeTreeElement>(new CodeTreeElement(rootFreq, null));
            newTree.setLeft(left);
            newTree.setRight(right);
            //adds new tree back into queue
            pq.add(newTree);
        }
        //returns the single tree in the priority queue
        return pq.peek();
    }

    @Override
    public Map<Character, String> computeCodes(BinaryTree<CodeTreeElement> codeTree) {
        Map<Character,String> codes = new HashMap<>();
        preOrder(codeTree, "", codes);
        return codes;
    }
    public void preOrder(BinaryTree<CodeTreeElement> root, String code, Map<Character,String> codes) {
        if (root == null) return;

        if(root.isLeaf()){
            codes.put(root.getData().getChar(), code);
            return;
        }
        preOrder(root.getLeft(), code + "0", codes);
        preOrder(root.getRight(),code + "1",codes);

    }

    @Override
    public void compressFile(Map<Character, String> codeMap, String pathName, String compressedPathName) throws IOException {

    }

    @Override
    public void decompressFile(String compressedPathName, String decompressedPathName, BinaryTree<CodeTreeElement> codeTree) throws IOException {

    }
}
