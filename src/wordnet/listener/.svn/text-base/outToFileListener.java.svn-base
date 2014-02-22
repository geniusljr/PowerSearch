package wordnet.listener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFileChooser;

import wordnet.base.PartOfSpeach;
import wordnet.base.Sense;
import wordnet.ui.MainWindow;
/**
 * 输出文件监听器,保存所有词性的词义/例句
 * @author Dragon
 *
 */
public class outToFileListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			JFileChooser chooser = new JFileChooser();
			if(JFileChooser.APPROVE_OPTION==chooser.showSaveDialog(MainWindow.mw))
			{
				File fileOut = chooser.getSelectedFile();
				PrintStream fw = null;
				try {
					if(!fileOut.exists()){
						fileOut.createNewFile();
					}
					FileOutputStream fos = new FileOutputStream(fileOut);
					fw = new PrintStream(fos);
					fw.println("Saved at:\n"+Calendar.getInstance().getTime());
					fw.println(MainWindow.mw.CurrentWord+":\n");
					PartOfSpeach[] pos = new PartOfSpeach[4];
					for (int i = 0; i < 4; i ++)
					{
						pos[i] = MainWindow.mw.word.getPOS(i);
						if (pos[i] != null){
							int count = 0;
							if(i==0)
								fw.println("Noun:\n");
							else if(i==1)
								fw.println("Adjective:\n");
							else if(i==2)
								fw.println("Verb:\n");
							else if(i==3)
								fw.println("Adverb:\n");
							else return;							
							
							List<Sense> tmpSense = pos[i].getSense();
							for(int j = 0; j<tmpSense.size(); j++){
								count++;
								fw.println("\nExpression"+count+":\n");
								String express = tmpSense.get(j).getExpression();
								fw.println(express);
								List<String> sample = tmpSense.get(j).getSamples();
								if(sample==null||sample.size()<1)
									continue;
								
								fw.println("\nSamples:\n");
								for(int k = 0; k < sample.size(); k++){
									fw.println(k+1+": "+sample.get(k));
								}
								fw.println("\n");
							}
							fw.println("\n\n");
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		}	
		
	}