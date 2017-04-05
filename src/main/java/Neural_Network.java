import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by nick on 4/3/17.
 */
public class Neural_Network {
  //input layer is first layer
  //output layer just holds weights associated with last layer of inner nodes
  private Layer[] layers;
  private Double learning_rate;

  Neural_Network(Integer hidden_layer_count, Integer[] hidden_layer_neuron_counts
    , Double learning_rate, Integer input_count)
  {
    this.learning_rate = learning_rate;

    layers = new Layer[hidden_layer_count + 2];

    //link input to nothing
    layers[0] = new Layer(input_count, 0);

    if(hidden_layer_count != 0) {
      //link hidden layer to input
      layers[1] = new Layer(hidden_layer_neuron_counts[0], 1);

      //link hidden layers to eachother
      if(hidden_layer_count > 1)
        for (int i = 1; i < hidden_layer_count; i++)
          layers[i + 1] = new Layer(hidden_layer_neuron_counts[i]
            , hidden_layer_neuron_counts[i - 1]);

      //link output to last hidden layer
      layers[layers.length - 1] = new Layer(0, hidden_layer_neuron_counts[hidden_layer_count - 1]);
    } else {
      //link output to input
      layers[layers.length - 1] = new Layer(1, input_count);
    }

  }

  public  void clearNet()
  {
    for (Layer layer: layers)
    {
      layer.initilizeNeurons();
    }
  }

  public void trainNet(String training_filename, Integer input_count)
  {
    try {
      List<String> trials = Files.readAllLines(Paths.get("~/Downloads/" + training_filename));
      for (String trial: trials) {
        String[] inputs_and_output = trial.split(" ");
        Double[] inputs = new Double[input_count];
        Double output = Double.parseDouble(inputs_and_output[input_count]);
        for (int i = 0; i < input_count; i++)
          inputs[i] = Double.parseDouble(inputs_and_output[i]);
        computeOutputs(inputs, output);
      }
    } catch(IOException e) {
      System.out.println("sorry bad file");
    }
  }

  private void computeOutputs(Double[] input_sigma, Double expected_output) {
    //set input's sigma to input values
    forwardPropagate(input_sigma);

    {
      //set output neuron to delta
      Neuron neuron = layers[layers.length - 1].neurons[0];
      neuron.delta = neuron.sigma * (1 - neuron.sigma) * (expected_output - neuron.sigma);
      for (int i = layers.length - 2; i >= 0; i--)
        computeDelta(layers[i], layers[i + 1]);
    }

    //recalculate weights
    for(int i = 1; i < layers.length; i++)
    {
      for (Neuron neuron: layers[i].neurons)
      {
        //weight and previous layer sigma's enumerated in parallel
        for(int j = 0; j < neuron.weights.length; j++)
          neuron.weights[j] += learning_rate * neuron.delta * layers[i - 1].neurons[j].sigma;

        //update bias weight
        neuron.bias_weight += learning_rate * neuron.delta;
      }
    }
  }

  public Double validateNet(String training_filename, Integer input_count)
  {
    try {
      List<String> trials = Files.readAllLines(Paths.get("~/Downloads/" + training_filename));
      Double sum = 0.0;
      for (String trial: trials) {
        String[] inputs_and_output = trial.split(" ");
        Double[] inputs = new Double[input_count];
        Double output = Double.parseDouble(inputs_and_output[input_count]);
        for (int i = 0; i < input_count; i++)
          inputs[i] = Double.parseDouble(inputs_and_output[i]);
        sum += validate(inputs, output);
      }
      Double root_mean_square_error = Math.sqrt((1/(2*trials.size()))*sum);

      return root_mean_square_error;
    } catch(IOException e) {
      System.out.println("sorry bad file");
      return 1.0;
    }
  }

  private Double validate(Double[] inputs, Double expected_output)
  {

    //set input's sigma to input values
    forwardPropagate(inputs);

    Double network_output = layers[layers.length - 1].neurons[0].sigma;

    return Math.pow(expected_output - network_output, 2);
  }

  private void forwardPropagate(Double[] inputs)
  {
    layers[0].setSigma(inputs);
    for (int i = 1; i < layers.length; i++)
      computeH(layers[i], layers[i - 1]);
  }

  private void computeH(Layer output, Layer input)
  {
    for(Neuron neuron: output.neurons)
    {
      //calculate h based on previous values
      for (int i = 0; i < input.neurons.length; i++)
        neuron.h += neuron.weights[i] * input.neurons[i].sigma;

      //add personal bias value into calculation
      neuron.h += neuron.bias_weight;

      //calculate current sigma value
      neuron.sigma = 1/(1+Math.exp(-neuron.h));
    }
  }

  public void computeDelta(Layer output, Layer input)
  {
    for (int i = 0; i < output.neurons.length; i++)
    {
      Neuron current_neuron = output.neurons[i];
      //this deals with that little rascal known as output neuron
      for (int j = 0; j < input.neurons.length; j++)
      {
        Neuron next_neuron = input.neurons[j];
        current_neuron.delta += current_neuron.sigma  * (1-current_neuron.sigma)
        * next_neuron.delta * next_neuron.bias_weight;
      }
    }
  }
}
