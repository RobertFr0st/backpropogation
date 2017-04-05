
/**
 * Created by nick on 4/3/17.
 */
public class Layer {
  public Neuron[] neurons;

  Layer(Integer neuron_count, Integer previous_layer_neuron_count)
  {
    neurons = new Neuron[neuron_count];
    for (int i = 0; i < neuron_count; i++) {
      neurons[i] = new Neuron(previous_layer_neuron_count);
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
    for (int i = 0; i < neurons.length; i++)
    {
      neurons[i].sigma = sigma[i];
    }
  }
}
