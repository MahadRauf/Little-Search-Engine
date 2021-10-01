package lse;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/*
 *
 * Little Search Engine Test App (LSETest)
 * Test application for LSE
 *
 */
public class LSETest {

    public static void main (String[] args) throws FileNotFoundException{

	// Change these values to use other documents
	String docsFile       = "docs.txt";
	String noiseWordsFile = "noisewords.txt";
	
	LittleSearchEngine lse = new LittleSearchEngine();
	lse.makeIndex(docsFile, noiseWordsFile);
	//////////////////////////////////////////////////////////////////////////
	/*ArrayList<Occurrence> arr = new ArrayList<Occurrence>();
	arr.add(new Occurrence("a.txt", 82));
	arr.add(new Occurrence("b.txt", 76));
	arr.add(new Occurrence("c.txt", 71));
	arr.add(new Occurrence("d.txt", 71));
	arr.add(new Occurrence("e.txt", 70));
	arr.add(new Occurrence("f.txt", 65));
	arr.add(new Occurrence("g.txt", 61));
	arr.add(new Occurrence("h.txt", 56));
	arr.add(new Occurrence("i.txt", 54));
	arr.add(new Occurrence("j.txt", 51));
	arr.add(new Occurrence("k.txt", 48));
	arr.add(new Occurrence("l.txt", 45));
	arr.add(new Occurrence("m.txt", 41));
	arr.add(new Occurrence("n.txt", 36));
	arr.add(new Occurrence("o.txt", 34));
	arr.add(new Occurrence("p.txt", 30));
	arr.add(new Occurrence("q.txt", 25));
	arr.add(new Occurrence("r.txt", 20));
	arr.add(new Occurrence("s.txt", 20));
	arr.add(new Occurrence("t.txt", 18));
	arr.add(new Occurrence("u.txt", 17));
	arr.add(new Occurrence("v.txt", 17));
	arr.add(new Occurrence("w.txt", 14));
	arr.add(new Occurrence("x.txt", 12));
		
	arr.add(new Occurrence("XOX.txt", 17)); //Term to sort!!
	ArrayList<Integer> p = lse.insertLastOccurrence(arr);
	System.out.println(p.toString());*/
	/*HashMap<String, Occurrence> t = lse.loadKeywordsFromDocument("jude.txt");
	for(String i : t.keySet()){
		System.out.print(i+", ");
	}
	System.out.println();*/
	//////////////////////////////////////////////////////////////////////////
	// Find the top 5 search using the words deep and world
	// Do other tests here to test your top5search() code.
	ArrayList<String> searchResult = lse.top5search("red", "car");
	if ( searchResult != null ) {
	    for (String res : searchResult) {
		System.out.println(res);
	    }
	} else {
	    System.out.println("top5search is returning null");
	}
    }

}
