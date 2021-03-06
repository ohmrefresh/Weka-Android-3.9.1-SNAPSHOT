package ark.wekaport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DeflaterInputStream;
import java.util.zip.DeflaterOutputStream;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    InputStream is = getResources().openRawResource(R.raw.ionosphere);
    BufferedReader datafile = new BufferedReader(new InputStreamReader(is));
    try {
      Instances m_Training = new Instances(datafile);
      m_Training.setClassIndex(m_Training.numAttributes() - 1);

      weka.filters.unsupervised.instance.Randomize m_Filter = new Randomize();
      m_Filter.setInputFormat(m_Training);

      Instances localInstances = Filter.useFilter(m_Training, m_Filter);
      RandomForest m_Classifier = new weka.classifiers.trees.RandomForest();

      m_Classifier.buildClassifier(localInstances);

      Evaluation m_Evaluation = new Evaluation(localInstances);
      m_Evaluation.crossValidateModel(m_Classifier, localInstances, 10,
          m_Training.getRandomNumberGenerator(1));
      String result = "Correct:"
          + m_Evaluation.correct()
          + "/Wrong:"
          + m_Evaluation.incorrect()
          + "/Correct(%):"
          + m_Evaluation.pctCorrect();
      Log.e("Result", result);
      Log.e("Result", "================================================");

      Toast.makeText(this, result, Toast.LENGTH_LONG).show();

      //Classification Instant

      List<String>  attZone=new ArrayList<>();
      Attribute attRef1 = new Attribute("ref1");
      Attribute attRef2 = new Attribute("ref2");
      Attribute attRef3 = new Attribute("ref3");

      ArrayList<Attribute>  atts=new ArrayList<>();
      atts.add(attRef1);
      atts.add(attRef2);
      atts.add(attRef3);
      atts.add(new Attribute("zone", attZone));



      Instances dataUnlabeled = new Instances("TestInstances", atts, 0);
      Instance iExample = new DenseInstance(3);
      iExample.setValue(atts.get(0), -22);
      iExample.setValue(atts.get(1), -33);
      iExample.setValue(atts.get(2), -33);
      dataUnlabeled.add(iExample);

      dataUnlabeled.setClassIndex(dataUnlabeled.numAttributes() - 1);

      double[] predict = m_Classifier.distributionForInstance(dataUnlabeled.firstInstance());

    } catch (Exception e) {

      e.printStackTrace();
    }



  }


  /**
   * Write Model To file
   * https://github.com/EsotericSoftware/kryo
   * @param m_Classifier
   */
  public void writeModel(Classifier m_Classifier){
    try {


      //Kryo kryo = new Kryo();
      //Output output = new Output(new FileOutputStream("file.bin"));
      //kryo.writeObject(output, m_Classifier);
      //output.close();


      OutputStream outputStream = new DeflaterOutputStream(new FileOutputStream("file.bin"));
      Output output = new Output(outputStream);
      Kryo kryo = new Kryo();
      kryo.writeObject(output, m_Classifier);
      output.close();

    }catch (Exception e){

    }
  }

  /**
   * Read training Object
   * https://github.com/EsotericSoftware/kryo
   * @return
   */
  public Classifier readModel(){
    try {
      Kryo kryo = new Kryo();
      //Input input = new Input(new FileInputStream("file.bin"));
      //
      //Classifier obj = kryo.readObject(input, Classifier.class);
      //input.close();

      InputStream input = new DeflaterInputStream(new FileInputStream("file.bin"));
      Classifier obj = kryo.readObject((Input) input, Classifier.class);
      input.close();


      return  obj;
    }catch (Exception e){

      return null;
    }
  }
}
