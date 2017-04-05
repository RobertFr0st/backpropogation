/**
 * Created by nick on 4/3/17.
 */
public class BackPropagation {
  public static void main(String [] args)
  {
    Integer hidden_layer_count = Integer.parseInt(args[0]);
    Double learning_rate = Double.parseDouble(args[1]);
    String training_filename = args[2];
    String validation_filename = args[3];
    String testing_filename = args[4];
    Integer epoch_count = Integer.parseInt(args[5]);
    Integer input_count = Integer.parseInt(args[6]);

    Integer[] hidden_layer_neuron_counts = new Integer[args.length - 8];
    for (int i = 8; i < args.length; i++) {
      hidden_layer_neuron_counts[i - 8] = Integer.parseInt(args[i]);
    }

    Neural_Network neural_network = new
      Neural_Network(hidden_layer_count, hidden_layer_neuron_counts, learning_rate, input_count);

    for(int i = 0; i < epoch_count; i++)
    {
      neural_network.clearNet();
      neural_network.trainNet(training_filename, input_count);
      Double score = neural_network.validateNet(validation_filename, input_count);
      System.out.println("Validation score: " + Double.toString(score) + " on Epoch: " + Integer.toString(i));
    }

    //test
    Double score = neural_network.validateNet(testing_filename, input_count);
    System.out.println("Testing score: " + Double.toString(score));
  }
}
