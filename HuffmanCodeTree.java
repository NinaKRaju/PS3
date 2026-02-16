/**
 * Problem Set #3
 *
 * @author Nina Raju
 * @author Erica Chemmanoor
 * Winter 2026, Dartmouth CS10
 */

import java.io.*;
import java.util.*;

public class HuffmanCodeTree implements Huffman {


    @Override
    public Map<Character, Long> countFrequencies(String pathName) throws IOException {
        Map<Character, Long> result = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(pathName));
        String line;
        while ((line = reader.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                Character c = line.charAt(i);
                if (!result.containsKey(c)) {
                    result.put(c, (long) 1);
                } else {
                    result.put(c, result.get(c) + 1);
                }
            }
        }
        return result;
    }

    @Override
    public BinaryTree<CodeTreeElement> makeCodeTree(Map<Character, Long> frequencies) {
        PriorityQueue<BinaryTree<CodeTreeElement>> pq = new PriorityQueue<>(new TreeComparator());
        //returns if file is empty
        if (frequencies.isEmpty()) {
            return null;
        }
        for (Character key : frequencies.keySet()) {
            //creates a new binary tree for each character and adds it to the priority tree
            CodeTreeElement current = new CodeTreeElement(frequencies.get(key), key);
            BinaryTree<CodeTreeElement> currentNode = new BinaryTree<>(current);
            pq.add(currentNode);

        }

        //huffman algorithm
        while (pq.size() > 1) {
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
        Map<Character, String> codes = new HashMap<>();
        preOrder(codeTree, "", codes);
        return codes;
    }

    public void preOrder(BinaryTree<CodeTreeElement> root, String code, Map<Character, String> codes) {
        if (root == null) return;

        if (root.isLeaf()) {
            codes.put(root.getData().getChar(), code);
            return;
        }
        preOrder(root.getLeft(), code + "0", codes);
        preOrder(root.getRight(), code + "1", codes);

    }

    @Override
    public void compressFile(Map<Character, String> codeMap, String pathName, String compressedPathName) throws IOException {
        // read characters from text file
        BufferedReader input = new BufferedReader(new FileReader(pathName));

        // takes an int that holds the unicode encoding of a character and casts it as a char
        int charUnicode = input.read();

        // checks if the file is empty
        while ((charUnicode != -1)) {

            // casts charUnicode to char
            char currentCharacter = (char) charUnicode;

            // look up the character's code word in the code map
            String codeWord = codeMap.get(currentCharacter);

            // write the sequences of 0's and 1's (the code word) as bits to an output file
            // creates new compressed file
            BufferedBitWriter bitOutput = new BufferedBitWriter(compressedPathName);

            // bits are boolean values false = 0 and true = 1
            boolean bit;
            for (int i = 0; i < codeWord.length(); i++) {
                char character = codeWord.charAt(i);
                // bit is true
                if (character == '1') {
                    bit = true;
                    bitOutput.writeBit(bit);
                }
                // bit is false
                if (character == '0') {
                    bit = false;
                    bitOutput.writeBit(bit);
                }
            }

            // closes file
            bitOutput.close();

            // updates charUnicode
            charUnicode = input.read();
        }

        // handles exception where file is empty
        if (charUnicode == -1) {
            throw new IOException("File is empty.");
        }

        // close file reader
        input.close();
    }

    @Override
    public void decompressFile(String compressedPathName, String decompressedPathName, BinaryTree<CodeTreeElement> codeTree) throws IOException {
        // creates bit reader to read bits from compressed file
        BufferedBitReader bitInput = new BufferedBitReader(compressedPathName);

        // creates plain text = decompressed file
        BufferedWriter output = new BufferedWriter(new FileWriter(decompressedPathName));

        // starts current node with the root
        BinaryTree<CodeTreeElement> currentNode = codeTree;

        // if there is a bit left to read from compressed file, then bit will be read and either a 0 or 1 will be written on the plain text
        while (bitInput.hasNext()) {
            boolean bit = bitInput.readBit();
            // runs down the code tree until a leaf is reached
            while (!currentNode.isLeaf()) {
                // if bit is true (1), go right
                if (bit == true) {
                    currentNode = codeTree.getRight();
                }
                // if bit is false (0), go left
                if (bit == false) {
                    currentNode = codeTree.getLeft();
                }
                // when a leaf is reached
                char decodedCharacter = currentNode.getData().getChar();
                output.write(decodedCharacter);
            }
            // after character is decoded, return to the root
            currentNode = codeTree;
        }
    }
}

