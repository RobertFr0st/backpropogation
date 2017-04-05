import java.util.Random;

/**
 * Created by nick on 4/3/17.
 */
public class Neuron {
  Double[] weights;
  //input neurons do not need bias
  Double bias_weight;
  Double delta;
  Double h;
  Double sigma;

  Neuron(Integer previous_layer_neuron_count)
  {
    weights = new Double[previous_layer_neuron_count];
    Random weight_range = new Random();
    double[] random_weights = weight_range.doubles(previous_layer_neuron_count,-0.1,0.1)
      .toArray();
    for (int i = 0; i < random_weights.length; i++) {
      weights[i] = random_weights[i];
    }

    bias_weight = weight_range.nextDouble();
  }
}
