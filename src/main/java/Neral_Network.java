import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 4/3/17.
 */
public class Neral_Network {
  //input layer is first layer
  //output layer just holds weights associated with last layer of inner nodes
  private ArrayList<Layer> layers;
  private Integer operator;

  public void setOperator(int operator){ this.operator = operator;}

  Neral_Network(Integer hidden_layer_count, Integer[] hidden_layer_neuron_counts
    , Integer learning_rate, Integer input_count)
  {
    layers = new ArrayList<>();

    if(hidden_layer_count != 0) {
      layers.add(new Layer(input_count, hidden_layer_neuron_counts[0]));
      if(hidden_layer_count > 1)
      {
        for (int i = 0; i < hidden_layer_count - 1; i++)
        {
          layers.add(new Layer(hidden_layer_neuron_counts[i]
            , hidden_layer_neuron_counts[i + 1]));
        }
        layers.add(new Layer(hidden_layer_neuron_counts[hidden_layer_count - 2], 1));
      } else {
        layers.add(new Layer(hidden_layer_neuron_counts[0], 1));
      }
    } else {
      layers.add(new Layer(input_count, 1));
    }

    layers.add(new Layer(1, 0));
  }


  public void trainNet(String training_filename, Integer input_count)
  {
    //clear outputs from previous epoch
    for (Layer layer: layers)
    {
      layer.initilizeNeurons();
    }

    try {
      List<String> trials = Files.readAllLines(Paths.get("~/Downloads/" + training_filename));
      for (String trial: trials) {
        String[] inputs_and_output = trial.split(" ");
        Double[] inputs = new Double[input_count];
        for (int i = 0; i < input_count; i++)
        {
          inputs[i] = Double.parseDouble(inputs_and_output[i]);
        }
        computeOutputs(inputs);
      }
    } catch(IOException e) {
      System.out.println("sorry bad file");
    }
  }

  private void computeOutputs(Double[] input_sigma){
    //set input's sigma to input values
    layers.get(0).setSigma(input_sigma);
  }
}
