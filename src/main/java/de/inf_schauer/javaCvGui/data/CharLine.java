package de.inf_schauer.javaCvGui.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import de.inf_schauer.javaCvGui.comparators.ImgObj2dXComparator;
import de.inf_schauer.javaCvGui.interfaces.I_ImgObj2d;

public class CharLine {

    SortedSet<I_ImgObj2d> line;

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private Stripe meanLine;

    public CharLine() {
        init();
    }

    public void init() {
        this.line = new TreeSet<>(new ImgObj2dXComparator());
        minX = -1;
        minY = -1;
        maxX = -1;
        maxY = -1;
        this.meanLine = new Stripe(0, 0);
    }

    public int getTopLine() {
        return maxY;
    }
    // public int getCapLine(){
    // //TODO
    // return 0;
    // }

    private int getMaxMinY(Set<I_ImgObj2d> seq) {
        Iterator<I_ImgObj2d> iter = seq.iterator();

        int result = 0;
        if (iter.hasNext()) {
            result = iter.next().getMinY();
        }

        while (iter.hasNext()) {
            int tmp = iter.next().getMinY();
            if (tmp > result) {
                result = tmp;
            }
        }
        return result;
    }

    private int getMaxXInYRange(int upperBound, int lowerBound, Set<I_ImgObj2d> seq) {
        int result = 0;
        Iterator<I_ImgObj2d> iter = seq.iterator();

        while (iter.hasNext()) {
            int tmp = iter.next().getMaxX();
            if (tmp < result && tmp <= upperBound && tmp >= lowerBound) {
                result = tmp;
            }
        }

        return result;
    }

    private int getMaxHeight() {
        Iterator<I_ImgObj2d> iter = line.iterator();

        int result = 0;
        if (iter.hasNext()) {
            result = iter.next().getMaxY();
        }
        while (iter.hasNext()) {
            int tmp = iter.next().getMaxY();
            if (tmp < result) {
                result = tmp;
            }
        }
        return result;
    }

    public Stripe getMeanLine() {
        int highestMean = getMaxMinY(line);
        int maxH = getMaxHeight();
        Stripe stripe = new Stripe(highestMean, highestMean - maxH / 4);
        return stripe;
    }

    public boolean isSmallChar(I_ImgObj2d o) {
        return meanLine.isInStripe(o.getMinY());
    }

    public int getBaseLine() {
        // TODO
        return minY;
    }

    public int getBeardline() {
        // TODO
        return 0;
    }

    public void add(I_ImgObj2d o) {
        updateMaxValues(o);
        line.add(o);
    }

    /**
     *
     * @param o
     */
    private void updateMaxValues(I_ImgObj2d o) {
        if (o.getMaxX() > maxX) {
            maxX = o.getMaxX();
        }
        if (o.getMinX() < minX) {
            minX = o.getMinX();
        }
        if (o.getMaxY() > maxY) {
            maxY = o.getMaxY();
        }
        if (o.getMinY() < minY) {
            minY = o.getMinY();
        }
        this.meanLine = getMeanLine();
    }

    public int getAverageDistance() {
        if (line.size() < 2) {
            return 0;
        }

        int divisor = line.size();
        int sum = 0;

        Iterator<I_ImgObj2d> iter = line.iterator();
        int tmp1;
        int tmp2;
        while (iter.hasNext()) {
            tmp1 = iter.next().getMinX();
            if (iter.hasNext()) {
                tmp2 = iter.next().getMinX();
                sum += tmp2 - tmp1;
            }
        }
        return sum / divisor;
    }

    public int getDistanceBetween(int c1, int c2) {
        List<I_ImgObj2d> list = new ArrayList<>(line);
        return list.get(c2).getMinX() - list.get(c1).getMinX();
    }

    public Set<I_ImgObj2d> getLine() {
        return line;
    }

    public int size() {
        return line.size();
    }

    public List<List<I_ImgObj2d>> getWords() {
        int threshold = getAverageDistance() * 3 / 2;
        List<List<I_ImgObj2d>> words = new ArrayList<>();

        LinkedList<I_ImgObj2d> word = new LinkedList<>();
        words.add(word);
        Iterator<I_ImgObj2d> iter = line.iterator();
        word.add(iter.next());
        while (iter.hasNext()) {
            I_ImgObj2d tmp = iter.next();
            if (tmp.getMinX() - word.getLast().getMaxX() < threshold) {
                word.add(tmp);
            } else {
                word = new LinkedList<>();
                words.add(word);
                word.add(tmp);
            }
        }

        return words;
    }
}
