package ark.wekaport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.filters.Filter;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    InputStream is = getResources().openRawResource(R.raw.ionosphere);
    BufferedReader datafile = new BufferedReader(new InputStreamReader(is));
    try {
      Instances m_Training = new Instances(datafile);
      m_Training.setClassIndex(m_Training.numAttributes() - 1);
      Filter m_Filter =
          ((Filter) Class.forName("weka.filters.unsupervised.instance.Randomize").newInstance());
      m_Filter.setInputFormat(m_Training);
      Instances localInstances = Filter.useFilter(m_Training, m_Filter);
      Classifier m_Classifier;
      m_Classifier = new weka.classifiers.trees.J48();
      m_Classifier.buildClassifier(localInstances);
      Evaluation m_Evaluation = new Evaluation(localInstances);

      m_Evaluation.crossValidateModel(m_Classifier, localInstances, 10,
          m_Training.getRandomNumberGenerator(1L));
      Log.e("Detail", m_Evaluation.toClassDetailsString());
      Log.e("Summary", m_Evaluation.toSummaryString());

      Log.e("Result", "================================================");
      Log.e("Result", "Correct:"
          + m_Evaluation.correct()
          + "/Wrong:"
          + m_Evaluation.incorrect()
          + "/Correct(%):"
          + m_Evaluation.pctCorrect());
      Log.e("Result", "================================================");
    } catch (Exception e) {

      e.printStackTrace();
    }
  }
}
