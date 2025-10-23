package ml;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;

public class WekaPredictor {
    private J48 model;

    public void trainModel(String csvPath) throws Exception {
        DataSource source = new DataSource(csvPath);
        Instances data = source.getDataSet();
        data.setClassIndex(data.numAttributes() - 1); // last column = grade 

        model = new J48(); // decision tree
        model.buildClassifier(data);
    }

    public String predict(String department, double score) throws Exception {
        Instances structure = new DataSource("employees.csv").getStructure();
        structure.setClassIndex(structure.numAttributes() - 1);

        weka.core.DenseInstance instance = new weka.core.DenseInstance(3);
        instance.setDataset(structure);
        instance.setValue(0, department);
        instance.setValue(1, score);

        double result = model.classifyInstance(instance);
        return structure.classAttribute().value((int) result);
    }
}
