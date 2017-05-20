package comparators;

import java.util.Comparator;

import items.RealWord;

public class RealWordDescendingComparator implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        // TODO Auto-generated method stub
        final RealWord paramOneRW = (RealWord) o1;
        final RealWord paramTwoRW = (RealWord) o2;
        
        return paramOneRW.getMyFreq().compareTo(paramTwoRW.getMyFreq());
    }

}
