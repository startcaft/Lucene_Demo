package cn.startcaft.com.lucene_006_analyzer_chinese;

import java.nio.file.Paths;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	
	
	//private static Analyzer analyzer = new StandardAnalyzer();//标准分词器, 这里使用标准分词器切分出来的中文单词或短语非常恶心
	private static Analyzer analyzer = new SmartChineseAnalyzer();//中文分词器
	
	private int[] ids = {1,2,3};
	private String[] cites = {"青岛","南京","上海"};
	private String descs[]={
			"青岛是一个美丽的城市。",
			"南京是一个有文化的城市。南京是一个文化的城市南京，简称宁，是江苏省会，地处中国东部地区，长江下游，濒江近海。全市下辖11个区，总面积6597平方公里，2013年建成区面积752.83平方公里，常住人口818.78万，其中城镇人口659.1万人。[1-4] “江南佳丽地，金陵帝王州”，南京拥有着6000多年文明史、近2600年建城史和近500年的建都史，是中国四大古都之一，有“六朝古都”、“十朝都会”之称，是中华文明的重要发祥地，历史上曾数次庇佑华夏之正朔，长期是中国南方的政治、经济、文化中心，拥有厚重的文化底蕴和丰富的历史遗存。[5-7] 南京是国家重要的科教中心，自古以来就是一座崇文重教的城市，有“天下文枢”、“东南第一学”的美誉。截至2013年，南京有高等院校75所，其中211高校8所，仅次于北京上海；国家重点实验室25所、国家重点学科169个、两院院士83人，均居中国第三。[8-10] 。",
			"上海是一个繁华的城市。"
	};
	
	private Directory dir = null;
	
	
	public static void main(String[] args) {
		
		try {
			new Indexer().index("D:\\LuceneIndexFiles\\Index006");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成索引
	 * @param indexDir
	 * @throws Exception
	 */
	public void index(String indexDir) throws Exception{
		
		dir = FSDirectory.open(Paths.get(indexDir, new String[0]));
		IndexWriter writer = getWriter();
		
		for (int i = 0; i < ids.length; i++) {
			Document doc = new Document();
			doc.add(new IntField("id", ids[i], Store.YES));
			doc.add(new StringField("city", cites[i], Store.YES));
			doc.add(new TextField("desc", descs[i], Store.YES));
			
			writer.addDocument(doc);
		}
		
		System.out.println("生成索引完毕");
		
		writer.close();
	}
	
	
	/**
	 * 获取IndexWriter实例
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter() throws Exception{
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, iwc);
		return writer;
	}
}
