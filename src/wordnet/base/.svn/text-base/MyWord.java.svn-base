package wordnet.base;
import java.util.*;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.*;
import net.didion.jwnl.dictionary.Dictionary;

public class MyWord{
	/**
	 * @author Dragon
	 */
	private String word;//要查的词
	private List<String> AntonymGroup;//反义词列表
	//private List<String> SynonymGroup;//同义词列表
	private IndexWord[] MyIndex;//对应的各个词性的IndexWord
	/**
	 * 0:NOUN
	 * 1:ADJECTIVE
	 * 2:VERB
	 * 3:ADVERB
	 */
	private PartOfSpeach[] POSGroup;
	
	{//初始化,四个词性
		POSGroup = new PartOfSpeach[4];
		for(int i = 0; i < 4; i++)
			POSGroup[i]=null;
	}
	/**
	 * @构造器
	 */
	public MyWord(){
	}
	public MyWord(String tmword){
		this.word = tmword;
		try{
			//MyIndex = Dictionary.getInstance().lookupAllIndexWords(word).getIndexWordArray();
			List<IndexWord> wordList = new ArrayList<IndexWord>();
			IndexWord Inword;
			Inword = Dictionary.getInstance().getIndexWord(POS.NOUN, word);
            if(Inword!=null){
            	wordList.add(Inword);
            }
            Inword = Dictionary.getInstance().getIndexWord(POS.ADJECTIVE, word);
            if(Inword!=null){
            	wordList.add(Inword);
            }
            Inword = Dictionary.getInstance().getIndexWord(POS.VERB, word);
            if(Inword!=null){
            	wordList.add(Inword);
            }
            Inword = Dictionary.getInstance().getIndexWord(POS.ADVERB, word);
            if(Inword!=null){
            	wordList.add(Inword);
            }
            MyIndex = new IndexWord[wordList.size()];
            for(int i = 0; i < wordList.size(); i++){
            	MyIndex[i] = wordList.get(i);
            }
			AntonymGroup = getAnt();
		}
		catch(JWNLException e){
			System.out.println("JWNLException");
		}
		catch(Exception e){
			System.out.println("Exception Occured!\n And is NOT a JWNLException!");
		}
	}
	/**
	 * @功能 返回同义词List的同时
	 * @功能 实现对所给词的POSGroup的初始化
	 * @return 同义词的list
	 */
	private List<String> getAnt(){
		List<String> antTmp = new ArrayList<String>();
		try{
			for(int i = 0; i < MyIndex.length; i++){
				if(MyIndex[i].getPOS()==POS.NOUN)
					search(MyIndex[i], 0);
				if(MyIndex[i].getPOS()==POS.ADJECTIVE)
					search(MyIndex[i], 1);
				if(MyIndex[i].getPOS()==POS.VERB)
					search(MyIndex[i], 2);
				if(MyIndex[i].getPOS()==POS.ADVERB)
					search(MyIndex[i], 3);
			}
			for(int i = 0; i < 4; i++){//将每个词性的所有Synset的所有反义词加入到List
				if(POSGroup[i]!=null){
					for(int j = 0; j < POSGroup[i].size(); j++){
						Synset syns = POSGroup[i].getSynset(j);
						//Version 1:
						Word[] words = syns.getWords();
						for(int k = 0; k < words.length; k++){
							Pointer[] pointers = words[k].getPointers(PointerType.ANTONYM);//反义词
							for(int l = 0; l < pointers.length; l++){
								antTmp.add(((Word)(pointers[l].getTarget())).getLemma());//添加反义词
							}
						}
						//Version 2:
						/*PointerTarget[] targets = syns.getTargets(PointerType.ANTONYM);
						for(int k = 0; k < targets.length; k++){
							
							if(targets[k].toString().startsWith("[S")){//是Synset
								for(int l = 0; l < ((Synset)targets[k]).getWordsSize(); l++){
									antTmp.add(((Synset)targets[k]).getWord(l).getLemma());
								}
							}
							else{//是WORD
								antTmp.add(((Word)targets[k]).getLemma());
							}
						}*/
	            	}
				}
			}
		}
		catch(Exception e){
			System.out.println("In class MyWord\nException Occured in Search_Function");
		}
		return antTmp;
	}
	/**
	 * 
	 * @param Mword
	 * @param type
	 * @功能 根据输入的IndexWord 即其词性
	 * @功能 查找其所有的Synset/
	 * @throws Exception
	 */
	private void search(IndexWord Mword, int type) throws Exception
	{
        if(POSGroup[type]==null){//某词性为空, 首先根据IndexWord构造一个
        	POSGroup[type] = new PartOfSpeach(Mword, type);
        }//否则直接在已有的基础上相加
        else{
        	POSGroup[type].add(Mword);
        }	
	}
	public PartOfSpeach getPOS(int type){
		return POSGroup[type];
	}
	public List<String> getAntonym(){
		return AntonymGroup;
	}
	public String getName(){
		return word;
	}
}