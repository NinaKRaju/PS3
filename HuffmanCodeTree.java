import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
        return null;
    }

    @Override
    public Map<Character, String> computeCodes(BinaryTree<CodeTreeElement> codeTree) {
        return Map.of();
    }

    @Override
    public void compressFile(Map<Character, String> codeMap, String pathName, String compressedPathName) throws IOException {

    }

    @Override
    public void decompressFile(String compressedPathName, String decompressedPathName, BinaryTree<CodeTreeElement> codeTree) throws IOException {

    }
}
