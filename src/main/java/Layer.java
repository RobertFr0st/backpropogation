import java.util.ArrayList;

/**
 * Created by nick on 4/3/17.
 */
public class Layer {
  private ArrayList<Neuron> neurons;

  Layer(Integer neuron_count, Integer next_layer_neuron_count)
  {
    for (int i = 0; i < neuron_count; i++) {
      neurons.add(new Neuron(next_layer_neuron_count));
    }
  }

  public void initilizeNeurons()
  {
    for (Neuron neuron: neurons)
    {
      neuron.delta = 0.0;
      neuron.h = 0.0;
      neuron.sigma = 0.0;
    }
  }

  public void setSigma(Double[] sigma)
  {
    for (int i = 0; i < neurons.size(); i++)
    {
      neurons.get(i).sigma = sigma[i];
    }
  }
}
