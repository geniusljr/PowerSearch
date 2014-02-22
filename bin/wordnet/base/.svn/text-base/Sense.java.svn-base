package wordnet.base;

import java.util.*;
import net.didion.jwnl.data.*;

public class Sense{
	private List<String> SynonymGroup;//同义词
	private String Expression="";//解释
	private List<String> SampleGroup;//例句
	private RelatedSense[] Related = new RelatedSense[30];
	private Synset syns;
	private Boolean bReady;
	{
		bReady = false;
		for(int i = 0; i < 30; i++){
			Related[i]=null;
		}
	}
	public Sense(Synset s){//根据传入的Synset 查找同义词/解释/例句
		syns = s;
		SynonymGroup = new ArrayList<String>();
		SampleGroup = new ArrayList<String>();
		
		String all = s.getGloss();
		for(int i = 0; i < s.getWordsSize(); i++){
			SynonymGroup.add(s.getWord(i).getLemma());//同义词
		}
		String[] splited = all.split(";");//示意;例句,例句... 
		int count = 0;
		for(int i = 0; i < splited.length; i++){
			char c = splited[i].charAt(1);
			if(c=='\"')
				SampleGroup.add(splited[i]);//例句
			else{
				if((++count)==1)
					Expression += (count)+". "+splited[i]+"\n";
				else
					Expression += (count)+"."+splited[i]+"\n";
			}
		}
	}
	/**
	 * 
	 * @return 同义词的list,可能有重复,使用时注意
	 */
	public List<String> getSynGroup(){
		if(SynonymGroup.size()<=0)
			return null;
		return SynonymGroup;
	}
	/**
	 * 
	 * @return 该Sense的英文解释
	 */
	public String getExpression(){
		return Expression;
	}
	/**
	 * 
	 * @return 该Sense相应的例句
	 */
	public List<String> getSamples(){
		return SampleGroup;
	}
	/**
	 * @注意 若某个Related[i]==null,则表示该关系为空
	 * @注解 数组对象为RelatedSense
	 * @return 所查Sense的相关Sense数组
	 */
	public RelatedSense getRelated(int index){
		if(!bReady){
			getRelate();
			bReady = true;
		}
		return Related[index];
	}
	private void getRelate(){
		try{
			PointerTarget[] targets = syns.getTargets(PointerType.ANTONYM);
			for(int j = 0; j < targets.length; j++){
				if(Related[0]==null)
					Related[0] = new RelatedSense("ANTONYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[0].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[0].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.ATTRIBUTE);
			for(int j = 0; j < targets.length; j++){
				if(Related[1]==null)
					Related[1] = new RelatedSense("ATTRIBUTE");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[1].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[1].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.CATEGORY);
			for(int j = 0; j < targets.length; j++){
				if(Related[2]==null)
					Related[2] = new RelatedSense("CATEGORY");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[2].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[2].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.CATEGORY_MEMBER );
			for(int j = 0; j < targets.length; j++){
				if(Related[3]==null)
					Related[3] = new RelatedSense("CATEGORY_MEMBER");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[3].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[3].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.CAUSE );
			for(int j = 0; j < targets.length; j++){
				if(Related[4]==null)
					Related[4] = new RelatedSense("CAUSE");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[4].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[4].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.DERIVED );
			for(int j = 0; j < targets.length; j++){
				if(Related[5]==null)
					Related[5] = new RelatedSense("DERIVED");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[5].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[5].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.ENTAILED_BY );
			for(int j = 0; j < targets.length; j++){
				if(Related[6]==null)
					Related[6] = new RelatedSense("ENTAILED_BY");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[6].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[6].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.ENTAILMENT );
			for(int j = 0; j < targets.length; j++){
				if(Related[7]==null)
					Related[7] = new RelatedSense("ENTAILMENT");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[7].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[7].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.HYPERNYM );
			for(int j = 0; j < targets.length; j++){
				if(Related[8]==null)
					Related[8] = new RelatedSense("HYPERNYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[8].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[8].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.HYPONYM );
			for(int j = 0; j < targets.length; j++){
				if(Related[9]==null)
					Related[9] = new RelatedSense("HYPONYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[9].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[9].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.INSTANCE_HYPERNYM );
			for(int j = 0; j < targets.length; j++){
				if(Related[10]==null)
					Related[10] = new RelatedSense("INSTANCE_HYPERNYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[10].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[10].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.INSTANCES_HYPONYM );
			for(int j = 0; j < targets.length; j++){
				if(Related[11]==null)
					Related[11] = new RelatedSense("INSTANCES_HYPONYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[11].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[11].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.MEMBER_HOLONYM );
			for(int j = 0; j < targets.length; j++){
				if(Related[12]==null)
					Related[12] = new RelatedSense("MEMBER_HOLONYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[12].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[12].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.MEMBER_MERONYM);
			for(int j = 0; j < targets.length; j++){
				if(Related[13]==null)
					Related[13] = new RelatedSense("MEMBER_MERONYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[13].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[13].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.NOMINALIZATION );
			for(int j = 0; j < targets.length; j++){
				if(Related[14]==null)
					Related[14] = new RelatedSense("NOMINALIZATION");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[14].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[14].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.PART_HOLONYM );
			for(int j = 0; j < targets.length; j++){
				if(Related[15]==null)
					Related[15] = new RelatedSense("PART_HOLONYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[15].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[15].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.PART_MERONYM);
			for(int j = 0; j < targets.length; j++){
				if(Related[16]==null)
					Related[16] = new RelatedSense("PART_MERONYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[16].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[16].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.PARTICIPLE_OF);
			for(int j = 0; j < targets.length; j++){
				if(Related[17]==null)
					Related[17] = new RelatedSense("PARTICIPLE_OF");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[17].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[17].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.PERTAINYM );
			for(int j = 0; j < targets.length; j++){
				if(Related[18]==null)
					Related[18] = new RelatedSense("PERTAINYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[18].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[18].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.REGION);
			for(int j = 0; j < targets.length; j++){
				if(Related[19]==null)
					Related[19] = new RelatedSense("REGION");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[19].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[19].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.REGION_MEMBER);
			for(int j = 0; j < targets.length; j++){
				if(Related[20]==null)
					Related[20] = new RelatedSense("REGION_MEMBER");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[20].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[20].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.SEE_ALSO);
			for(int j = 0; j < targets.length; j++){
				if(Related[21]==null)
					Related[21] = new RelatedSense("SEE_ALSO");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[21].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[21].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.SIMILAR_TO );
			for(int j = 0; j < targets.length; j++){
				if(Related[22]==null)
					Related[22] = new RelatedSense("SIMILAR_TO");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[22].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[22].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.SUBSTANCE_HOLONYM );
			for(int j = 0; j < targets.length; j++){
				if(Related[23]==null)
					Related[23] = new RelatedSense("SUBSTANCE_HOLONYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[23].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[23].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.SUBSTANCE_MERONYM);
			for(int j = 0; j < targets.length; j++){
				if(Related[24]==null)
					Related[24] = new RelatedSense("SUBSTANCE_MERONYM");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[24].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[24].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.USAGE );
			for(int j = 0; j < targets.length; j++){
				if(Related[25]==null)
					Related[25] = new RelatedSense("USAGE");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[25].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[25].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.USAGE_MEMBER );
			for(int j = 0; j < targets.length; j++){
				if(Related[26]==null)
					Related[26] = new RelatedSense("USAGE_MEMBER");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[26].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[26].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
			targets = syns.getTargets(PointerType.VERB_GROUP );
			for(int j = 0; j < targets.length; j++){
				if(Related[27]==null)
					Related[27] = new RelatedSense("VERB_GROUP");
				if(targets[j].toString().startsWith("[S")){//向RelatedSense的Sense list中添加新的Sense;
					Related[27].add(new Sense((Synset)targets[j]));
				}
				else{
					Related[27].add(new Sense(((Word)targets[j]).getSynset()));
				}
			}
		}
		catch(Exception e){
			System.out.println("In class Sense\n Exception Occured in getRelated_function");
		};
	}
}