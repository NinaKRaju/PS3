import java.util.Comparator;

public class TreeComparator implements Comparator<BinaryTree<CodeTreeElement>>{

    @Override
    public int compare(BinaryTree<CodeTreeElement> o1, BinaryTree<CodeTreeElement> o2) {
        if(o1.getData().getFrequency().equals(o2.getData().getFrequency())){
            return 0;
        }
        else if(o1.getData().getFrequency() > o2.getData().getFrequency()){
            return 1;
        }
        else{
            return -1;
        }
    }
}
