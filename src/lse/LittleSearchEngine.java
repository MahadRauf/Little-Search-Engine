package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		/*File tempFile = new File(docFile);
		if(!tempFile.exists()){
			throw new FileNotFoundException("Could not find file");
		}*/
		HashMap<String,Occurrence> ret = new HashMap<String, Occurrence>();
		Scanner sc = new Scanner(new File(docFile));
		while(sc.hasNext()){
			String word = sc.next();
			word = getKeyword(word);
			if(word == null){
				continue;
			}else{
				if(ret.containsKey(word)){
					Occurrence temp = ret.get(word);
					temp.frequency = temp.frequency + 1;
					ret.replace(word, temp);
				}else{
					Occurrence temp = new Occurrence(docFile, 1);
					ret.put(word, temp);
				}
			}
		}
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return ret;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		// for String i in kws?
		// yeah idk I'll look at this later*
		// probably gotta redo this and insertLastOcuurrence
		for (String i : kws.keySet()){
			if(!keywordsIndex.containsKey(i)){
				ArrayList<Occurrence> toAdd = new ArrayList<Occurrence>();
				toAdd.add(kws.get(i));
				insertLastOccurrence(toAdd);
				keywordsIndex.put(i, toAdd);
			}else if(keywordsIndex.containsKey(i)){
				ArrayList<Occurrence> toAdd = keywordsIndex.get(i);
				toAdd.add(kws.get(i));
				insertLastOccurrence(toAdd);
				keywordsIndex.replace(i, toAdd);
			}
		}
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation(s), consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * NO OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
	 * 
	 * If a word has multiple trailing punctuation characters, they must all be stripped
	 * So "word!!" will become "word", and "word?!?!" will also become "word"
	 * 
	 * See assignment description for examples
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		// probably doesnt work right idk Im going to sleep
		String temp = word.toLowerCase();
		int numToRemove = 0;
		int l = word.length();
		for(int i = l-1; i >= 0; i--){
			if((temp.charAt(i) >= 'a')&&(temp.charAt(i) <= 'z')){
				break;
			}
			if((temp.charAt(i)=='.')||(temp.charAt(i)==',')||(temp.charAt(i)=='?')||(temp.charAt(i)==':')||(temp.charAt(i)==';')||(temp.charAt(i)=='!')){
				numToRemove++;
			}
		}
		l = l - numToRemove;
		char [] cArr = new char[l];
		for(int i = 0; i < l; i++){
			if(!((temp.charAt(i) >= 'a')&&(temp.charAt(i) <= 'z'))){
				return null;
			}
			cArr[i] = temp.charAt(i);
		}
		String ret = String.valueOf(cArr);
		if(noiseWords.contains(ret)){
			return null;
		}
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return ret;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		// honestly have no clue what this method is even for or does
		if (occs.size() == 1){
			return null;
		}
		ArrayList<Integer> ret = new ArrayList<Integer>();
		Occurrence temp = occs.get(occs.size()-1);
		int freq = temp.frequency;
		occs.remove(occs.size()-1);

		int hi = occs.size()-1;
		int lo = 0;
		int mid = 0;

		while(lo<=hi){
			mid = lo + (hi - lo)/2;
			int midF = occs.get(mid).frequency;
			if(midF == freq){
				ret.add(mid);
				break;
			}else if(midF < freq){
				hi = mid - 1;
				ret.add(mid);
			}else if(midF > freq){
				lo = mid + 1;
				ret.add(mid);
				mid++;
			}
		}
		//ret = search(temp, occs, lo, hi, ret);
		//int i = ret.get(ret.size()-1);
		occs.add(mid, temp);
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		//return search(temp, occs, lo, hi, ret);
		return ret;
	}

	/*private ArrayList<Integer> search(Occurrence occ, ArrayList<Occurrence> occs, int lo, int hi, ArrayList<Integer> ret){
		int mid = lo + (hi - lo)/2;
		if(hi <= lo){
			return ret;
		}
		//int mid = lo + (hi - lo)/2;
		int cmpr = occs.get(mid).frequency;
		int occF = occ.frequency;
		if(occF > cmpr){
			ret.add(mid);
			return search(occ, occs, lo, mid, ret);
		}else if(occF < cmpr){
			ret.add(mid);
			return search(occ, occs, mid+1, hi, ret);
		}else{
			ret.add(mid);
			return ret;
		}
	}*/
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. 
	 * 
	 * Note that a matching document will only appear once in the result. 
	 * 
	 * Ties in frequency values are broken in favor of the first keyword. 
	 * That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same 
	 * frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * See assignment description for examples
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, 
	 *         returns null or empty array list.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		ArrayList<String> ret = new ArrayList<String>();
		if(keywordsIndex.containsKey(kw1) && keywordsIndex.containsKey(kw2)){
			ArrayList<Occurrence> kw1Occ = keywordsIndex.get(kw1);
			ArrayList<Occurrence> kw2Occ = keywordsIndex.get(kw2);
			int itr1 = 0;
			int itr2 = 0;
			int count = 0;
			while((itr1 < kw1Occ.size()) && (itr2 < kw2Occ.size()) && (count < 5)){
				int freq1 = kw1Occ.get(itr1).frequency;
				int freq2 = kw2Occ.get(itr2).frequency;
				if(freq1 == freq2){
					if(!ret.contains(kw1Occ.get(itr1).document)){
						ret.add(kw1Occ.get(itr1).document);
						///////////////////////
						count++;
						if(!(count < 5)){
							break;
						}
					}
					if(!ret.contains(kw2Occ.get(itr2).document)){
						ret.add(kw2Occ.get(itr2).document);
						///////////////////////
						count++;
						if(!(count < 5)){
							break;
						}
					}
					itr1++;
					itr2++;
				}else if(freq1 > freq2){
					if(!ret.contains(kw1Occ.get(itr1).document)){
						ret.add(kw1Occ.get(itr1).document);
						///////////////////////
						count++;
						if(!(count < 5)){
							break;
						}
					}
					itr1++;
				}else if(freq1 < freq2){
					if(!ret.contains(kw2Occ.get(itr2).document)){
						ret.add(kw2Occ.get(itr2).document);
						///////////////////////
						count++;
						if(!(count < 5)){
							break;
						}
					}
					itr2++;
				}
				//count++;
			}
			if((itr1 < kw1Occ.size()) && (count < 5)){
				while((itr1 < kw1Occ.size()) && (count < 5)){
					if(!ret.contains(kw1Occ.get(itr1).document)){
						ret.add(kw1Occ.get(itr1).document);
						count++;
					}
					itr1++;
				}
			}
			if((itr2 < kw2Occ.size()) && (count < 5)){
				while((itr2 < kw2Occ.size()) && (count < 5)){
					if(!ret.contains(kw2Occ.get(itr2).document)){
						ret.add(kw2Occ.get(itr2).document);
						count++;
					}
					itr2++;
				}
			}
		}else if(keywordsIndex.containsKey(kw1)){
			ArrayList<Occurrence> kw1Occ = keywordsIndex.get(kw1);
			int i = 0;
			int count = 0;
			while((i < kw1Occ.size()) && (count < 5)){
				ret.add(kw1Occ.get(i).document);
				i++;
				count++;
			}
		}else if(keywordsIndex.containsKey(kw2)){
			ArrayList<Occurrence> kw2Occ = keywordsIndex.get(kw2);
			int i = 0;
			int count = 0;
			while((i < kw2Occ.size()) && (count < 5)){
				ret.add(kw2Occ.get(i).document);
				i++;
				count++;
			}
		}else{
			return null;
		}
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return ret;
	
	}
}
