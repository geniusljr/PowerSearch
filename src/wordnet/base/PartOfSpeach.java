package wordnet.base;

import java.util.*;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Synset;
/**
 * @属性 一个PartOfSpeach包含该一个词性的所有Synset(or Sense)
 * @author Dragon
 * @方法 getSense
 */
public class PartOfSpeach{
	private List<Synset> SynsetGroup;
	private List<Sense> SenseGroup;
	private String pos;
	public PartOfSpeach(IndexWord word, int type){
		SynsetGroup = new ArrayList<Synset>();
		SenseGroup = new ArrayList<Sense>();
		switch(type){
			case 0:
				pos = "NOUN";
				break;
			case 1:
				pos = "ADJECTIVE";
				break;
			case 2:
				pos = "VERB";
				break;
			case 3:
				pos = "ADVERB";
				break;
		}
		try{
			Synset[] syns = word.getSenses();
			for(int i = 0; i < syns.length; i++){
				SynsetGroup.add(syns[i]);
				SenseGroup.add(new Sense(syns[i]));
			}
		}
		catch(Exception e){
			System.out.println("in class PartOfSpeach\nException Occured in constructor");
		}
	}
	/**
	 * @功能 根据 IndexWord 添加Synset至list中
	 * @param word
	 */
	public void add(IndexWord word){
		try{
			Synset[] syns = word.getSenses();
			for(int i = 0; i < syns.length; i++){
				SynsetGroup.add(syns[i]);
				SenseGroup.add(new Sense(syns[i]));
			}
		}
		catch(Exception e){
			System.out.println("in class PartOfSpeach\nException Occured in add_function");
		}
	}
	public int size(){
		return SynsetGroup.size();
	}
	public Synset getSynset(int k){
		return SynsetGroup.get(k);
	}
	public List<Sense> getSense(){
		if(SenseGroup.size()<=0)
			return null;
		return SenseGroup;
	}
	/**
	 * 
	 * @return 所在组的词性
	 */
	public String getType(){
		return pos;
	}
}