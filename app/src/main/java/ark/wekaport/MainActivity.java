package ark.wekaport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    InputStream is = getResources().openRawResource(R.raw.test222);
    BufferedReader datafile = new BufferedReader(new InputStreamReader(is));
    try {
      Instances m_Training = new Instances(datafile);
      m_Training.setClassIndex(m_Training.numAttributes() - 1);

      weka.filters.unsupervised.instance.Randomize m_Filter = new Randomize();
      m_Filter.setInputFormat(m_Training);

      Instances localInstances = Filter.useFilter(m_Training, m_Filter);
      RandomForest m_Classifier = new weka.classifiers.trees.RandomForest();

      m_Classifier.buildClassifier(localInstances);




      //Instances test = new Instances(m_Training);

      //try {
      //  train = new Instances(data, 0, mid);
      //  test = new Instances(data, mid, tot - mid);
      //  Classifier m_Classifier2 = new SerializedClassifier();

      // save
      //SerializationHelper.write(MODEL_FILENAME, classifier);
      //  m_Classifier.setModelFile(new File(MODEL_FILENAME));
      //} catch (Exception e) {
      //  e.printStackTrace();
      //  fail("Problem setting up to use classifier: " + e);
      //}
      //
      //EvaluationUtils evaluation = new EvaluationUtils();
      //evaluation.getTrainTestPredictions()
      ////try {
      ////  trainAndSerializeClassifier(train);
      //
      //ArrayList<Prediction> regressionResults;
      //  regressionResults = evaluation.getTrainTestPredictions(m_Classifier,
      //      m_Training, test);
      //
      //Log.e("RES", predictionsToString(regressionResults));
      //} catch (Exception e) {
      //  fail("Failed obtaining classifier predictions: " + e);
      //}

      //
      //

      //
      //Log.e("Result", "====================="+10+"===========================");
      //m_Evaluation.crossValidateModel(m_Classifier, localInstances, 10, m_Training.getRandomNumberGenerator(1));
      //Log.e("Result", "Correct:"
      //    + m_Evaluation.correct()
      //    + "/Wrong:"
      //    + m_Evaluation.incorrect()
      //    + "/Correct(%):"
      //    + m_Evaluation.pctCorrect());
      //Log.e("Result", "================================================");

      //crossValidateModel(Classifier classifier, Instances data, int numFolds, Random random,
      //    Object... forPredictionsPrinting)
      //crossValidateModel(String classifierString, Instances data, int numFolds,
      //String[] options, Random random)

      Evaluation m_Evaluation = new Evaluation(localInstances);
      m_Evaluation.evaluateModel(m_Classifier, localInstances);
      List<Prediction> predictionList = m_Evaluation.predictions();
      for (int i = 0; i < predictionList.size(); i++) {
        NominalPrediction value = (NominalPrediction) predictionList.get(i);
        if (value.margin() < 0.9) {  //Less than 0.9(90%) will remove training
          // TODO: 1/6/2559
          // remove training instant
        }
      }



      //
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

  protected String predictionsToString(ArrayList<Prediction> predictions) {
    StringBuffer sb = new StringBuffer();
    sb.append(predictions.size()).append(" predictions\n");
    for (int i = 0; i < predictions.size(); i++) {
      sb.append(predictions.get(i)).append('\n');
    }
    return sb.toString();
  }
}
