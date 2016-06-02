# Weka-Android-3.9.1-SNAPSHOT
Port Weka Java Application version Weka-3.9.1-SNAPSHOT to Android Library 
* Remove GUI Component
* Remote RMI Component

##Reference
    Weka is a collection of machine learning algorithms for data mining tasks. The algorithms can either be applied directly to a dataset or called from your own Java code. Weka contains tools for data pre-processing, classification, regression, clustering, association rules, and visualization. It is also well-suited for developing new machine learning schemes.
    
    Found only on the islands of New Zealand, the Weka is a flightless bird with an inquisitive nature. The name is pronounced like this, and the bird sounds like this.
    
    Weka is open source software issued under the GNU General Public License.

http://www.cs.waikato.ac.nz/ml/weka/svn.html

####Example


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

####Todo list
* add Test
* Optimize performance

