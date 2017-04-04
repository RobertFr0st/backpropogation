import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nick on 4/3/17.
 */
public class Neuron {
  private ArrayList<Double> weights;
  //input neurons do not need bias
  private Double bias_weight;
  Double delta;
  Double h;
  Double sigma;

  Neuron(Integer next_layer_neuron_count)
  {
    Random weight_range = new Random();
    double[] random_weights = weight_range.doubles(next_layer_neuron_count,-0.1,0.1)
      .toArray();
    for (int i = 0; i < random_weights.length; i++) {
      weights.add(random_weights[i]);
    }

    bias_weight = weight_range.nextDouble();
  }
}
